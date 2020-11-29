package br.com.portabilit.wsmarcaponto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portabilit.wsmarcaponto.domain.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

	@Transactional(readOnly = true)
	Optional<Colaborador> findByEmail(String email);

	@Transactional(readOnly = true)
	List<Colaborador> findByAtivo(Boolean ativo);

	@Transactional(readOnly = true)
	@Query(nativeQuery = true, value = "SELECT * FROM colaborador WHERE funcao_id IN (SELECT id FROM funcao WHERE funcao_responsavel_id IN (SELECT funcao_id FROM colaborador WHERE id = :gestorId))")
	List<Colaborador> findByGestorId(@Param(value = "gestorId") Integer gestorId);

}
