package br.com.portabilit.wsmarcaponto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portabilit.wsmarcaponto.domain.Expediente;

@Repository
public interface ExpedienteRepository extends JpaRepository<Expediente, Integer> {

	@Transactional(readOnly = true)
	Optional<Expediente> findByNome(String nome);

	@Transactional(readOnly = true)
	List<Expediente> findByAtivo(Boolean ativo);

}
