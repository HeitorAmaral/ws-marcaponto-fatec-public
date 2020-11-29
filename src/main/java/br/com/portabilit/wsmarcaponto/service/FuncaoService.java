package br.com.portabilit.wsmarcaponto.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Funcao;
import br.com.portabilit.wsmarcaponto.domain.dto.FuncaoNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.FuncaoUpdateDTO;
import br.com.portabilit.wsmarcaponto.repository.FuncaoRepository;
import br.com.portabilit.wsmarcaponto.service.exception.DataIntegrityException;
import br.com.portabilit.wsmarcaponto.service.exception.GenericException;
import br.com.portabilit.wsmarcaponto.service.exception.ObjectNotFoundException;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.FuncaoValidation;

@Service
public class FuncaoService {

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private SetorService setorService;

	@Autowired
	private FuncaoValidation funcaoValidation;

	@Autowired
	private TextBuilderService textBuilderService;

	public List<Funcao> findAll() {
		try {
			return funcaoRepository.findAll();
		} catch (Exception e) {
			throw new GenericException(
					textBuilderService.getGenericMethodExceptionForNoneParameter("Função", "findAll", e.toString()));
		}
	}

	public List<Funcao> findByAtivo(Boolean ativo) {
		try {
			return funcaoRepository.findByAtivo(ativo);
		} catch (Exception e) {
			String[] parameters = { ativo.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Função",
					"findByAtivo", parameters, e.toString()));
		}
	}

	public Funcao findById(Integer id) {
		try {
			return funcaoRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException(
					textBuilderService.getValidationExceptionForNotFoundRegister("Função", "funcaoId", id.toString()));
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Função",
					"findById", parameters, e.toString()));
		}
	}

	public Optional<Funcao> findByIdForValidation(Integer id) {
		try {
			return funcaoRepository.findById(id);
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Função",
					"findByIdForValidation", parameters, e.toString()));
		}
	}

	public List<Funcao> findBySetorId(Integer setorId) {
		setorService.findById(setorId);
		try {
			return funcaoRepository.findBySetorId(setorId);
		} catch (Exception e) {
			String[] parameters = { setorId.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Função",
					"findBySetorId", parameters, e.toString()));
		}
	}

	public List<Funcao> findBySetorIdAndAtivo(Integer setorId, Boolean ativo) {
		setorService.findById(setorId);
		try {
			return funcaoRepository.findBySetorIdAndAtivo(setorId, ativo);
		} catch (Exception e) {
			String[] parameters = { setorId.toString(), ativo.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Função",
					"findBySetorIdAndAtivo", parameters, e.toString()));
		}
	}

	public Optional<Funcao> findByNomeAndSetorId(String nome, Integer setorId) {
		try {
			return funcaoRepository.findByNomeAndSetorId(nome, setorId);
		} catch (Exception e) {
			String[] parameters = { nome, setorId.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Função",
					"findByNomeAndSetorId", parameters, e.toString()));
		}
	}

	public Funcao insert(FuncaoNewDTO funcaoNewDTO) {
		funcaoValidation.validateNewFuncao(funcaoNewDTO);

		return save(new Funcao(funcaoNewDTO.getNome(), funcaoNewDTO.getFuncaoResponsavelId(),
				(findById(funcaoNewDTO.getFuncaoResponsavelId()).getNome().toUpperCase().equals("MASTER") ? true
						: funcaoNewDTO.getResponsavel()),
				funcaoNewDTO.getSetorId()));

	}

	public void update(FuncaoUpdateDTO funcaoUpdateDTO, Integer id) {
		Funcao funcao = new Funcao();
		funcao = findById(id);

		funcaoValidation.validateUpdateFuncao(funcaoUpdateDTO, id);

		funcao.setNome(funcaoUpdateDTO.getNome());
		funcao.setFuncaoResponsavelId(funcaoUpdateDTO.getFuncaoResponsavelId());
		funcao.setResponsavel((findById(funcao.getFuncaoResponsavelId()).getNome().toUpperCase().equals("MASTER") ? true
				: funcaoUpdateDTO.getResponsavel()));
		funcao.setSetorId(funcaoUpdateDTO.getSetorId());
		funcao.setAtivo(funcaoUpdateDTO.getAtivo());

		save(funcao);
	}

	public Funcao save(Funcao funcao) {
		try {
			return funcaoRepository.save(funcao);
		} catch (DataIntegrityViolationException e) {
			String[] parameters = { "funcao" };
			throw new DataIntegrityException(textBuilderService.getIntegrityViolationMethodExceptionForMultiParameters(
					"Função", "save", parameters, e.getMostSpecificCause().toString()));
		} catch (Exception e) {
			String[] parameters = { "funcao" };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Função", "save",
					parameters, e.toString()));
		}
	}

	public void deleteById(Integer id) {
		findById(id);
		funcaoValidation.validateDeleteFuncao(id);
		try {
			funcaoRepository.deleteById(id);
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Função",
					"deleteById", parameters, e.toString()));
		}
	}
}
