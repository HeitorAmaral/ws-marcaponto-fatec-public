package br.com.portabilit.wsmarcaponto.service.security;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.security.Usuario;
import br.com.portabilit.wsmarcaponto.repository.security.UsuarioRepository;
import br.com.portabilit.wsmarcaponto.service.exception.DataIntegrityException;
import br.com.portabilit.wsmarcaponto.service.exception.GenericException;
import br.com.portabilit.wsmarcaponto.service.exception.ObjectNotFoundException;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private TextBuilderService textBuilderService;

	public Usuario findById(Integer id) {
		try {
			return usuarioRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException(textBuilderService.getValidationExceptionForNotFoundRegister("Usuario",
					"usuarioId", id.toString()));
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Usuario",
					"findById", parameters, e.toString()));
		}
	}

	public Usuario findByUsername(String username) {
		try {
			return usuarioRepository.findByUsername(username);
		} catch (Exception e) {
			String[] parameters = { username };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Usuario",
					"findByUsername", parameters, e.toString()));
		}
	}

	public Usuario findByColaboradorId(Integer colaboradorId) {
		try {
			return usuarioRepository.findByColaboradorId(colaboradorId);
		} catch (Exception e) {
			String[] parameters = { colaboradorId.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Usuario",
					"findByColaboradorId", parameters, e.toString()));
		}
	}

	public static Usuario authenticated() {
		try {
			return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return new Usuario("Master", "Master", 1, true);
		}
	}

	public Usuario changePassword(String password) {
		Usuario usuario = new Usuario();
		usuario = authenticated();
		usuario.setPassword(bCryptPasswordEncoder.encode(password));
		return save(usuario);
	}

	public Usuario save(Usuario usuario) {
		try {
			return usuarioRepository.save(usuario);
		} catch (DataIntegrityViolationException e) {
			String[] parameters = { "usuario" };
			throw new DataIntegrityException(textBuilderService.getIntegrityViolationMethodExceptionForMultiParameters(
					"Usuario", "save", parameters, e.getMostSpecificCause().toString()));
		} catch (Exception e) {
			String[] parameters = { "usuario" };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Usuario", "save",
					parameters, e.toString()));
		}
	}

	public void deleteById(Integer id) {
		findById(id);
		try {
			usuarioRepository.deleteById(id);
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Usuario",
					"deleteById", parameters, e.toString()));
		}
	}

}
