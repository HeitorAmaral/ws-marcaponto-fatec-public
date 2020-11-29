package br.com.portabilit.wsmarcaponto.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Colaborador;
import br.com.portabilit.wsmarcaponto.domain.Funcao;
import br.com.portabilit.wsmarcaponto.domain.dto.ColaboradorNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.ColaboradorUpdateDTO;
import br.com.portabilit.wsmarcaponto.domain.security.Usuario;
import br.com.portabilit.wsmarcaponto.domain.security.enums.Perfil;
import br.com.portabilit.wsmarcaponto.repository.ColaboradorRepository;
import br.com.portabilit.wsmarcaponto.service.email.EmailService;
import br.com.portabilit.wsmarcaponto.service.exception.DataIntegrityException;
import br.com.portabilit.wsmarcaponto.service.exception.GenericException;
import br.com.portabilit.wsmarcaponto.service.exception.ObjectNotFoundException;
import br.com.portabilit.wsmarcaponto.service.security.UsuarioService;
import br.com.portabilit.wsmarcaponto.service.utils.PasswordGenerator;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.ColaboradorValidation;

@Service
public class ColaboradorService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private FuncaoService funcaoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ColaboradorRepository colaboradorRepository;

	@Autowired
	private ColaboradorValidation colaboradorValidation;

	@Autowired
	private TextBuilderService textBuilderService;

	public List<Colaborador> findAll() {
		try {
			return colaboradorRepository.findAll();
		} catch (Exception e) {
			throw new GenericException(textBuilderService.getGenericMethodExceptionForNoneParameter("Colaborador",
					"findAll", e.toString()));
		}
	}

	public Colaborador findById(Integer id) {
		try {
			return colaboradorRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException(textBuilderService
					.getValidationExceptionForNotFoundRegister("Colaborador", "colaboradorId", id.toString()));
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Colaborador",
					"findById", parameters, e.toString()));
		}
	}

	public Optional<Colaborador> findByIdForValidation(Integer id) {
		try {
			return colaboradorRepository.findById(id);
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Colaborador",
					"findByIdForValidation", parameters, e.toString()));
		}
	}

	public Optional<Colaborador> findByEmail(String email) {
		try {
			return colaboradorRepository.findByEmail(email);
		} catch (Exception e) {
			String[] parameters = { email.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Colaborador",
					"findByEmail", parameters, e.toString()));
		}
	}

	public List<Colaborador> findByAtivo(Boolean ativo) {
		try {
			return colaboradorRepository.findByAtivo(ativo);
		} catch (Exception e) {
			String[] parameters = { ativo.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Colaborador",
					"findByAtivo", parameters, e.toString()));
		}
	}

	public List<Colaborador> findByGestorId(Integer gestorId) {
		try {
			return colaboradorRepository.findByGestorId(gestorId);
		} catch (Exception e) {
			String[] parameters = { gestorId.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Colaborador",
					"findByGestorId", parameters, e.toString()));
		}
	}

	public Colaborador insert(ColaboradorNewDTO colaboradorNewDTO) {
		colaboradorValidation.validateNewColaborador(colaboradorNewDTO);

		Colaborador colaborador = new Colaborador(colaboradorNewDTO.getNome(), colaboradorNewDTO.getEmail(),
				colaboradorNewDTO.getFuncaoId(), colaboradorNewDTO.getExpedienteId(), true);
		colaborador = save(colaborador);

		String defaultPassword = PasswordGenerator.getRandomPassword();

		Usuario usuario = new Usuario(colaborador.getEmail(), bCryptPasswordEncoder.encode(defaultPassword),
				colaborador.getId(), colaborador.getAtivo());

		if (funcaoService.findById(colaborador.getFuncaoId()).getResponsavel()) {
			usuario.addPerfil(Perfil.RESPONSAVEL);
		}

		usuarioService.save(usuario);
		emailService.sendRegisterConfirmationEmail(usuario.getId(), colaborador.getNome(), colaborador.getEmail(),
				defaultPassword);

		return colaborador;
	}

	public void update(ColaboradorUpdateDTO colaboradorUpdateDTO, Integer id) {
		Colaborador colaborador = new Colaborador();
		colaborador = findById(id);

		colaboradorValidation.validateUpdateColaborador(colaboradorUpdateDTO, id);

		Usuario usuario = usuarioService.findByColaboradorId(colaborador.getId());

		colaborador.setNome(colaboradorUpdateDTO.getNome());

		if (colaborador.getEmail() != colaboradorUpdateDTO.getEmail()) {
			colaborador.setEmail(colaboradorUpdateDTO.getEmail());
			usuario.setUsername(colaborador.getEmail());
		}

		Funcao old_funcao = funcaoService.findById(colaborador.getFuncaoId());
		Funcao new_funcao = funcaoService.findById(colaboradorUpdateDTO.getFuncaoId());

		if (old_funcao != new_funcao) {
			colaborador.setFuncaoId(colaboradorUpdateDTO.getFuncaoId());
			if (new_funcao.getResponsavel()) {
				usuario.addPerfil(Perfil.RESPONSAVEL);
			} else {
				if (usuario.getPerfis().contains(Perfil.RESPONSAVEL)) {
					usuario.removePerfil(Perfil.RESPONSAVEL);
				}
			}
		}

		colaborador.setExpedienteId(colaboradorUpdateDTO.getExpedienteId());

		if (colaborador.getAtivo() != colaboradorUpdateDTO.getAtivo()) {
			colaborador.setAtivo(colaboradorUpdateDTO.getAtivo());
			usuario.setEnabled(colaborador.getAtivo());
		}

		save(colaborador);
		usuarioService.save(usuario);
	}

	public Colaborador save(Colaborador colaborador) {
		try {
			return colaboradorRepository.save(colaborador);
		} catch (DataIntegrityViolationException e) {
			String[] parameters = { "colaborador" };
			throw new DataIntegrityException(textBuilderService.getIntegrityViolationMethodExceptionForMultiParameters(
					"Colaborador", "save", parameters, e.getMostSpecificCause().toString()));
		} catch (Exception e) {
			String[] parameters = { "colaborador" };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Colaborador",
					"save", parameters, e.toString()));
		}
	}

	public void deleteById(Integer id) {
		findById(id);
		try {
			colaboradorRepository.deleteById(id);
			usuarioService.deleteById(usuarioService.findByColaboradorId(id).getId());
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Colaborador",
					"deleteById", parameters, e.toString()));
		}
	}
}
