package br.com.financeiro.integration.config;

import br.com.mesadigital.financeiro.controller.dto.CaixaDTO;
import br.com.mesadigital.financeiro.controller.dto.CaixaPatchDTO;
import br.com.mesadigital.financeiro.controller.dto.RelatorioCaixaDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CaixaScenario {

    public static CaixaDTO getCaixaCompleto(){
        return new CaixaDTO(LocalDateTime.now(),"ALEX",new BigDecimal("10"),new BigDecimal(120));
    }

    public static CaixaDTO getCaixaCompletoFechado(){
        return new CaixaDTO(LocalDateTime.now(),"ALEX", LocalDateTime.now().plusHours(8), new BigDecimal(10),new BigDecimal(120));
    }

    public static CaixaPatchDTO getCaixaCompletoEditado(){
        return new CaixaPatchDTO(1L,LocalDateTime.now().plusHours(2));
    }

    public static RelatorioCaixaDTO getRelatorioCaixa() {
        return new RelatorioCaixaDTO("ALEX","10-01-2027T08:00:00","",new BigDecimal("0"));
    }
}
