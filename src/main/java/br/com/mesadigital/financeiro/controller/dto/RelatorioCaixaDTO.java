package br.com.mesadigital.financeiro.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class RelatorioCaixaDTO {

    @NotBlank
    String nomeUsuario;

    @NotBlank
    String dataHoraAbertura;

    String dataHoraFechamento;

    BigDecimal troco;

    public RelatorioCaixaDTO() {
    }

    public RelatorioCaixaDTO(String nomeUsuario, String dataHoraAbertura, String dataHoraFechamento, BigDecimal troco) {
        this.nomeUsuario = nomeUsuario;
        this.dataHoraAbertura = dataHoraAbertura;
        this.dataHoraFechamento = dataHoraFechamento;
        this.troco = troco;
    }

    public @NotBlank String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(@NotBlank String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(String dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public String getDataHoraFechamento() {
        return dataHoraFechamento;
    }

    public void setDataHoraFechamento(String dataHoraFechamento) {
        this.dataHoraFechamento = dataHoraFechamento;
    }

    public BigDecimal getTroco() {
        return troco;
    }

    public void setTroco(BigDecimal troco) {
        this.troco = troco;
    }
}
