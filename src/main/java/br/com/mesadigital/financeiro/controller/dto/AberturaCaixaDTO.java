package br.com.mesadigital.financeiro.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class AberturaCaixaDTO {

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHoraAbertura;

    @NotBlank
    private String idUsuario;

    public LocalDateTime getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public @NotBlank String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(@NotBlank String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
