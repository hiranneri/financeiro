package br.com.mesadigital.financeiro.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CaixaDTO {

    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHoraAbertura;

    @NotBlank
    private String nomeUsuario;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHoraFechamento;

    private BigDecimal totalCaixa;

    private BigDecimal trocoCaixa;

    public CaixaDTO(LocalDateTime dataHoraAbertura, String nomeUsuario, LocalDateTime dataHoraFechamento, BigDecimal trocoCaixa, BigDecimal totalCaixa) {
        this.dataHoraAbertura = dataHoraAbertura;
        this.nomeUsuario = nomeUsuario;
        this.dataHoraFechamento = dataHoraFechamento;
        this.trocoCaixa = trocoCaixa;
        this.totalCaixa = totalCaixa;
    }

    public CaixaDTO(LocalDateTime dataHoraAbertura, String nomeUsuario, BigDecimal trocoCaixa, BigDecimal totalCaixa) {
        this.dataHoraAbertura = dataHoraAbertura;
        this.nomeUsuario = nomeUsuario;
        this.trocoCaixa = trocoCaixa;
        this.totalCaixa = totalCaixa;
    }

    public CaixaDTO(Long id, LocalDateTime dataHoraAbertura, String nomeUsuario, LocalDateTime dataHoraFechamento, BigDecimal trocoCaixa, BigDecimal totalCaixa) {
        this.id = id;
        this.dataHoraAbertura = dataHoraAbertura;
        this.nomeUsuario = nomeUsuario;
        this.dataHoraFechamento = dataHoraFechamento;
        this.trocoCaixa = trocoCaixa;
        this.totalCaixa = totalCaixa;
    }

    public CaixaDTO() {
    }

    public CaixaDTO(Long id, LocalDateTime dataHoraAbertura, LocalDateTime dataHoraFechamento, BigDecimal troco, BigDecimal valorTotal) {
        this.id = id;
        this.dataHoraAbertura = dataHoraAbertura;
        this.dataHoraFechamento = dataHoraFechamento;

        BigDecimal trocoTratado = troco == null ? new BigDecimal(0) : troco;
        BigDecimal valorTotalTratado = valorTotal == null ? new BigDecimal(0) : valorTotal;

        this.trocoCaixa = trocoTratado;
        this.totalCaixa = valorTotalTratado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull LocalDateTime getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(@NotNull LocalDateTime dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public @NotBlank String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(@NotBlank String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public LocalDateTime getDataHoraFechamento() {
        return dataHoraFechamento;
    }

    public void setDataHoraFechamento(LocalDateTime dataHoraFechamento) {
        this.dataHoraFechamento = dataHoraFechamento;
    }

    public BigDecimal getTrocoCaixa() {
        return trocoCaixa;
    }

    public void setTrocoCaixa(BigDecimal trocoCaixa) {
        this.trocoCaixa = trocoCaixa;
    }

    public BigDecimal getTotalCaixa() {
        return totalCaixa;
    }

    public void setTotalCaixa(BigDecimal totalCaixa) {
        this.totalCaixa = totalCaixa;
    }
}
