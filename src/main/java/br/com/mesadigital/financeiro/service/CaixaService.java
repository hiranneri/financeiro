package br.com.mesadigital.financeiro.service;

import br.com.mesadigital.financeiro.config.dto.PagamentoDTO;
import br.com.mesadigital.financeiro.controller.dto.AberturaCaixaDTO;
import br.com.mesadigital.financeiro.controller.dto.CaixaDTO;
import br.com.mesadigital.financeiro.controller.dto.CaixaPatchDTO;
import br.com.mesadigital.financeiro.controller.dto.RelatorioCaixaDTO;
import br.com.mesadigital.financeiro.model.Caixa;
import br.com.mesadigital.financeiro.model.Pagamento;
import br.com.mesadigital.financeiro.repository.CaixaRepository;
import br.com.mesadigital.financeiro.repository.PagamentoRepository;
import br.com.mesadigital.financeiro.utils.DataUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CaixaService {

    @Autowired
    CaixaRepository caixaRepository;

    @Autowired
    PagamentoRepository pagamentoRepository;

    ObjectMapper mapper = new ObjectMapper();

    private final KafkaTemplate<String, String> kafkaPedidoTemplate;

    public CaixaService(KafkaTemplate<String, String> kafkaPedidoTemplate) {
        this.kafkaPedidoTemplate = kafkaPedidoTemplate;
        // Registrando o módulo para Java 8 Time API (LocalDateTime, LocalDate, etc)
        mapper.registerModule(new JavaTimeModule());

        // Configurando o formato de datas
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Transactional
    public CaixaDTO abrir(CaixaDTO caixaDTO) throws BadRequestException {

            Long idUsuario = pesquisarIdUsuario(caixaDTO.getNomeUsuario()); // Confirmar se o id é long ou String
            Caixa caixaJahAberto = pesquisarSeTemCaixaAbertoParaOUsuario(caixaDTO,idUsuario);

            if(caixaJahAberto != null) {
                throw new BadRequestException("Este caixa já está aberto com esse usuário e data de abertura");
            }

            Caixa novoCaixa = new Caixa(idUsuario);

            BeanUtils.copyProperties(caixaDTO, novoCaixa);

            caixaRepository.save(novoCaixa);

            BeanUtils.copyProperties(novoCaixa, caixaDTO);
            return caixaDTO;
    }

    private Caixa pesquisarSeTemCaixaAbertoParaOUsuario(CaixaDTO caixaDTO, Long idUsuario) {
        return caixaRepository.findCaixaByDataHoraFechamentoAndIdUsuario(null, idUsuario);
    }

    public CaixaPatchDTO atualizarPeloId(CaixaPatchDTO caixaDTO) {
        try {
            Caixa caixaASerEditado = pesquisarCaixaPeloId(caixaDTO.getId());
            BeanUtils.copyProperties(caixaDTO,caixaASerEditado);
            caixaRepository.save(caixaASerEditado);
            return caixaDTO;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    private Caixa pesquisarCaixaPeloId(Long idCaixa) throws BadRequestException{
        return caixaRepository.findById(idCaixa).orElseThrow(() -> new BadRequestException("Caixa não encontrado"));
    }

    Long pesquisarIdUsuario(String nomeUsuario) {
        return 1L;
    }


    public Page<CaixaDTO> buscarCaixas(@Valid RelatorioCaixaDTO filtroCaixa, Pageable pageable) {
        LocalDateTime dataHoraAbertura = DataUtils.toPTBR(filtroCaixa.getDataHoraAbertura());
        List<Caixa> caixasLocalizados;

        if(filtroCaixa.getDataHoraFechamento() == null) {
            caixasLocalizados = caixaRepository.findByIdUsuarioAndDataHoraAbertura(1L, dataHoraAbertura, pageable).getContent();
        } else {
            LocalDateTime dataHoraFechamento = DataUtils.toPTBR(filtroCaixa.getDataHoraFechamento());
            caixasLocalizados = caixaRepository.findByIdUsuarioAndDataHoraAberturaAndDataHoraFechamento(
                    1L, // ir ao ms ou cache
                    dataHoraAbertura,
                    dataHoraFechamento,
                    pageable
            ).getContent();
        }

        List<CaixaDTO> caixasDTO = new ArrayList<>();

        for(Caixa caixa: caixasLocalizados){
            caixasDTO.add(toCaixaDTO(caixa));
        }
        return new PageImpl<>(caixasDTO);
    }

    private CaixaDTO toCaixaDTO(Caixa caixa) {
        CaixaDTO caixaDTO = new CaixaDTO();
        caixaDTO.setId(caixa.getId());
        BigDecimal troco = caixa.getTroco();
        caixaDTO.setTrocoCaixa(troco==null? new BigDecimal(0):troco);
        caixaDTO.setDataHoraAbertura(caixa.getDataHoraAbertura());
        caixaDTO.setDataHoraFechamento(caixa.getDataHoraFechamento());
        caixaDTO.setNomeUsuario("MOCK");  // ir ao cache
        return caixaDTO;
    }

    public CaixaPatchDTO fechar(@Valid CaixaPatchDTO caixaDTO) throws BadRequestException {

            Caixa caixaASerFechado = pesquisarCaixaPeloId(caixaDTO.getId());

            if(caixaASerFechado.getDataHoraFechamento() != null) {
                throw new BadRequestException("Caixa já está fechado!");
            }

            BeanUtils.copyProperties(caixaDTO,caixaASerFechado);

            caixaASerFechado.setDataHoraFechamento(caixaDTO.getDataHoraFechamento());

            caixaRepository.save(caixaASerFechado);
            return caixaDTO;

    }

    /**
     * Verifica se o usuário já possui um caixa aberto
     * @param aberturaCaixaDTO Dados de abertura do caixa
     * @return caixaDTO
     * @throws BadRequestException Caso o caixa esteja fechado
     */
    public CaixaDTO pesquisarCaixaPelaDataAberturaEResponsavel(@Valid AberturaCaixaDTO aberturaCaixaDTO) throws BadRequestException {
        Caixa caixaLocalizado = caixaRepository.findFirstByOrderByIdUsuarioDesc(Long.parseLong(aberturaCaixaDTO.getIdUsuario()));
        if(caixaLocalizado == null || caixaLocalizado.getDataHoraFechamento() != null) {
            throw new BadRequestException("Caixa não localizado ou já fechado!");
        }
        CaixaDTO caixaDTO = new CaixaDTO(caixaLocalizado.getId(),caixaLocalizado.getDataHoraAbertura(), caixaLocalizado.getDataHoraFechamento(),caixaLocalizado.getTroco(), caixaLocalizado.getValorTotal());
        BeanUtils.copyProperties(caixaLocalizado,caixaDTO);
        return caixaDTO;
    }

    @KafkaListener(topics = "pedido-criado", groupId = "financeiro-service")
    public void obterPedidoCriado(String pedidoCriado) {
        try {
            PagamentoDTO pagamento = mapper.reader().forType(PagamentoDTO.class).readValue(pedidoCriado);
            cadastrarPagamento(pagamento);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    void cadastrarPagamento(PagamentoDTO pagamentoDTO) throws JsonProcessingException {
        pagamentoRepository.save(new Pagamento(pagamentoDTO));
        kafkaPedidoTemplate.send("pedido-quitado", mapper.writeValueAsString(pagamentoDTO));
    }

}
