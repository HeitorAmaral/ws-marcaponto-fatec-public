package br.com.portabilit.wsmarcaponto.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portabilit.wsmarcaponto.domain.security.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	@Transactional(readOnly = true)
	Usuario findByUsername(String username);

	@Transactional(readOnly = true)
	Usuario findByColaboradorId(Integer colaboradorId);
}
