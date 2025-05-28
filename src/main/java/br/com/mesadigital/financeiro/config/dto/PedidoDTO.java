package br.com.mesadigital.financeiro.config.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDTO(Long id, BigDecimal total, LocalDateTime dataHora, List<String> formasPagamento) {
}
