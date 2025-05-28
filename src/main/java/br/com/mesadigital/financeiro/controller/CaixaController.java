package br.com.mesadigital.financeiro.controller;

import br.com.mesadigital.financeiro.controller.dto.AberturaCaixaDTO;
import br.com.mesadigital.financeiro.controller.dto.CaixaDTO;
import br.com.mesadigital.financeiro.controller.dto.CaixaPatchDTO;
import br.com.mesadigital.financeiro.controller.dto.RelatorioCaixaDTO;
import br.com.mesadigital.financeiro.service.CaixaService;
import br.com.mesadigital.financeiro.utils.DataUtils;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caixas")
public class CaixaController {

    @Autowired
    CaixaService caixaService;

    @PostMapping
    public ResponseEntity<CaixaDTO> abrir(@RequestBody @Valid CaixaDTO caixaDTO) throws BadRequestException {
        return new ResponseEntity<CaixaDTO>(caixaService.abrir(caixaDTO), HttpStatus.CREATED);
    }

    @PatchMapping("fechar/{id}")
    public ResponseEntity<CaixaPatchDTO> fecharCaixa(@PathVariable Long id, @Valid @RequestBody CaixaPatchDTO caixaDTO) throws BadRequestException {
        caixaDTO.setId(id);
        return new ResponseEntity<CaixaPatchDTO>(caixaService.fechar(caixaDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CaixaDTO>> buscar(@Valid RelatorioCaixaDTO filtroCaixa, Pageable pageable) {
        return ResponseEntity.ok(caixaService.buscarCaixas(filtroCaixa,pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CaixaPatchDTO> atualizarPeloId(@PathVariable Long id, @Valid @RequestBody CaixaPatchDTO caixaDTO) {
        caixaDTO.setId(id);
        return new ResponseEntity<CaixaPatchDTO>(caixaService.atualizarPeloId(caixaDTO), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<CaixaDTO> pesquisarCaixaPelaDataAberturaEResponsavel(@RequestParam String dataHoraAbertura, @RequestParam String idUsuario) throws BadRequestException {
        AberturaCaixaDTO aberturaCaixaDTO = new AberturaCaixaDTO();
        aberturaCaixaDTO.setDataHoraAbertura(DataUtils.toPTBR(dataHoraAbertura));
        aberturaCaixaDTO.setIdUsuario(idUsuario);

        return new ResponseEntity<CaixaDTO>(caixaService.pesquisarCaixaPelaDataAberturaEResponsavel(aberturaCaixaDTO), HttpStatus.OK);
    }


}
