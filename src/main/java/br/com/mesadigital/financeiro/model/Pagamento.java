package br.com.mesadigital.financeiro.model;

import br.com.mesadigital.financeiro.config.dto.PagamentoDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal totalPedido;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private String formasPagamento;

    @Column(nullable = false)
    private BigDecimal troco;

    @Column(nullable = false)
    private Long nomeUsuario;

    public Pagamento(Long id, BigDecimal totalPedido, LocalDateTime dataHora, String formasPagamento, BigDecimal troco, Long nomeUsuario) {
        this.id = id;
        this.totalPedido = totalPedido;
        this.dataHora = dataHora;
        this.formasPagamento = formasPagamento;
        this.troco = troco;
        this.nomeUsuario = nomeUsuario;
    }

    public Pagamento(PagamentoDTO pagamentoDTO) {
        this.totalPedido = pagamentoDTO.totalPedido();
        this.dataHora = pagamentoDTO.dataHora();
        this.formasPagamento = pagamentoDTO.formasPagamento();
        this.troco = pagamentoDTO.troco();
        this.nomeUsuario = pagamentoDTO.nomeUsuario();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(BigDecimal totalPedido) {
        this.totalPedido = totalPedido;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getFormasPagamento() {
        return formasPagamento;
    }

    public void setFormasPagamento(String formasPagamento) {
        this.formasPagamento = formasPagamento;
    }

    public BigDecimal getTroco() {
        return troco;
    }

    public void setTroco(BigDecimal troco) {
        this.troco = troco;
    }

    public Long getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(Long nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}
