package br.com.mesadigital.financeiro.repository;

import br.com.mesadigital.financeiro.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
