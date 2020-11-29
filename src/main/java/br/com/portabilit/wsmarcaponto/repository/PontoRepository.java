package br.com.portabilit.wsmarcaponto.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portabilit.wsmarcaponto.domain.Ponto;

@Repository
public interface PontoRepository extends JpaRepository<Ponto, Integer> {

	@Transactional(readOnly = true)
	@Query(nativeQuery = true, value = "SELECT * FROM ponto WHERE colaborador_id IN (SELECT id FROM colaborador WHERE funcao_id IN (SELECT id FROM funcao WHERE funcao_responsavel_id IN (SELECT funcao_id FROM colaborador WHERE id = :colaboradorId))) AND status = 0")
	List<Ponto> findToApproveByColaboradorId(@Param(value = "colaboradorId") Integer colaboradorId);

	@Transactional(readOnly = true)
	@Query(nativeQuery = true, value = "SELECT * FROM ponto WHERE colaborador_id = :colaboradorId AND status = :statusDoPonto")
	List<Ponto> findByColaboradorIdAndStatus(@Param(value = "colaboradorId") Integer colaboradorId,
			@Param(value = "statusDoPonto") Integer statusDoPonto);

	@Transactional(readOnly = true)
	List<Ponto> findByColaboradorId(Integer colaboradorId);

	@Transactional(readOnly = true)
	List<Ponto> findByDataAndColaboradorId(Date data, Integer colaboradorId);

}
