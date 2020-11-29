package br.com.portabilit.wsmarcaponto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portabilit.wsmarcaponto.domain.Setor;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Integer> {

	@Transactional(readOnly = true)
	Optional<Setor> findByNome(String nome);

	@Transactional(readOnly = true)
	List<Setor> findByAtivo(Boolean ativo);

}
