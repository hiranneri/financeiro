package br.com.financeiro.integration;

import br.com.mesadigital.financeiro.controller.dto.CaixaDTO;
import br.com.mesadigital.financeiro.controller.dto.CaixaPatchDTO;
import br.com.mesadigital.financeiro.controller.dto.RelatorioCaixaDTO;
import br.com.mesadigital.financeiro.utils.DataUtils;
import br.com.financeiro.integration.config.CaixaScenario;
import br.com.financeiro.integration.config.TestContainerConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AA_CaixaControllerIT extends TestContainerConfig {

    CaixaDTO caixa = CaixaScenario.getCaixaCompleto();

    @BeforeEach
    void setUp(){
        // Registrando o módulo para Java 8 Time API (LocalDateTime, LocalDate, etc)
        objectMapper.registerModule(new JavaTimeModule());

        // Configurando o formato de datas
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    @DisplayName("Abertura de caixa")
    void AA_RealizaAberturaCaixa() throws Exception {

        mockMvc.perform(
                        post("/caixas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(caixa))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.dataHoraFechamento").doesNotExist())
                .andExpect(jsonPath("$.dataHoraAbertura").value(
                        DataUtils.toPTBR(caixa.getDataHoraAbertura())
                ));

    }

    @Test
    @DisplayName("Tenta abrir um caixa pela segunda vez")
    void AB_TentarAbrirCaixaPelaSegundaVez() throws Exception {

        mockMvc.perform(
                post("/caixas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CaixaScenario.getCaixaCompleto()))
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Realizar fechamento de caixa")
    void AC_RealizarFechamentoCaixa() throws Exception {
        CaixaDTO caixaDTO = CaixaScenario.getCaixaCompletoFechado();

        mockMvc.perform(
                        patch("/caixas/fechar/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(caixaDTO))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.dataHoraFechamento")
                        .value(DataUtils.toPTBR(caixaDTO.getDataHoraFechamento()))
                );
    }

    @Test
    @DisplayName("Tenta fechar um caixa pela segunda vez")
    void AD_FecharCaixaPelaSegundaVez() throws Exception {

        mockMvc.perform(
                patch("/caixas/fechar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CaixaScenario.getCaixaCompletoFechado()))
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Atualizar um caixa")
    void AF_AtualizarCaixa() throws Exception {
        CaixaPatchDTO caixaPatchDTO = CaixaScenario.getCaixaCompletoEditado();
        mockMvc.perform(
                        patch("/caixas/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(caixaPatchDTO))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.dataHoraFechamento").value(DataUtils.toPTBR(caixaPatchDTO.getDataHoraFechamento())));
    }

}
