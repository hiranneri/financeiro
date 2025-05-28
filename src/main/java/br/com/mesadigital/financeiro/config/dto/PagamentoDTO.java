package br.com.mesadigital.financeiro.config.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoDTO(BigDecimal totalPedido, LocalDateTime dataHora, String formasPagamento, BigDecimal troco, Long nomeUsuario) {
}
