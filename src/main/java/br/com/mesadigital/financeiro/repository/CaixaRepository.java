package br.com.mesadigital.financeiro.repository;

import br.com.mesadigital.financeiro.model.Caixa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long> {

    Page<Caixa> findByIdUsuarioAndDataHoraAberturaAndDataHoraFechamento(Long idUsuario, LocalDateTime dataHoraAbertura,
                                                                        LocalDateTime dataHoraFechamento, Pageable pageable);

    Page<Caixa> findByIdUsuarioAndDataHoraAbertura(Long idUsuario, LocalDateTime dataHoraAbertura, Pageable pageable);

    Caixa findCaixaByDataHoraFechamentoAndIdUsuario(LocalDateTime dataFechamento, Long idUsuario);

    @Query("SELECT c FROM Caixa c WHERE c.idUsuario=:idUsuario ORDER BY c.id DESC LIMIT 1")
    Caixa findFirstByOrderByIdUsuarioDesc(Long idUsuario);

}
