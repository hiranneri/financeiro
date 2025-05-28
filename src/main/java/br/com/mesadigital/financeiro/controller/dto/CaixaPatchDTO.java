package br.com.mesadigital.financeiro.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

public class CaixaPatchDTO {

    @Min(1)
    Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHoraFechamento;

    public @Min(1) Long getId() {
        return id;
    }

    public void setId(@Min(1) Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHoraFechamento() {
        return dataHoraFechamento;
    }

    public void setDataHoraFechamento(LocalDateTime dataHoraFechamento) {
        this.dataHoraFechamento = dataHoraFechamento;
    }

    public CaixaPatchDTO() {
    }

    public CaixaPatchDTO(Long id, LocalDateTime dataHoraFechamento) {
        this.id = id;
        this.dataHoraFechamento = dataHoraFechamento;
    }
}
