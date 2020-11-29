package br.com.portabilit.wsmarcaponto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portabilit.wsmarcaponto.domain.Funcao;

@Repository
public interface FuncaoRepository extends JpaRepository<Funcao, Integer> {

	@Transactional(readOnly = true)
	Optional<Funcao> findByNomeAndSetorId(String nome, int setorId);

	@Transactional(readOnly = true)
	List<Funcao> findByAtivo(boolean ativo);

	@Transactional(readOnly = true)
	@Query(value = "SELECT * FROM funcao WHERE setor_id = ?1", nativeQuery = true)
	List<Funcao> findBySetorId(int id);

	@Transactional(readOnly = true)
	List<Funcao> findBySetorIdAndAtivo(Integer setorId, Boolean ativo);

}
