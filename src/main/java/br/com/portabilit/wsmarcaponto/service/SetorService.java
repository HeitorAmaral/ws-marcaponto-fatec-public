package br.com.portabilit.wsmarcaponto.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Setor;
import br.com.portabilit.wsmarcaponto.domain.dto.SetorNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.SetorUpdateDTO;
import br.com.portabilit.wsmarcaponto.repository.SetorRepository;
import br.com.portabilit.wsmarcaponto.service.exception.DataIntegrityException;
import br.com.portabilit.wsmarcaponto.service.exception.GenericException;
import br.com.portabilit.wsmarcaponto.service.exception.ObjectNotFoundException;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.SetorValidation;

@Service
public class SetorService {

	@Autowired
	private SetorRepository setorRepository;

	@Autowired
	private SetorValidation setorValidation;

	@Autowired
	private TextBuilderService textBuilderService;

	public List<Setor> findAll() {
		try {
			return setorRepository.findAll();
		} catch (Exception e) {
			throw new GenericException(textBuilderService.getGenericMethodExceptionForNoneParameter("Setor",
					"findAll", e.toString()));
		}
	}

	public Setor findById(Integer id) {
		try {
			return setorRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException(textBuilderService.getValidationExceptionForNotFoundRegister("Setor",
					"setorId", id.toString()));
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Setor",
					"findById", parameters, e.toString()));
		}
	}

	public Optional<Setor> findByIdForValidation(Integer id) {
		try {
			return setorRepository.findById(id);
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Setor",
					"findByIdForValidation", parameters, e.toString()));
		}
	}

	public List<Setor> findByAtivo(Boolean ativo) {
		try {
			return setorRepository.findByAtivo(ativo);
		} catch (Exception e) {
			String[] parameters = { ativo.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Setor",
					"findByAtivo", parameters, e.toString()));
		}
	}

	public Optional<Setor> findByNome(String nome) {
		try {
			return setorRepository.findByNome(nome);
		} catch (Exception e) {
			String[] parameters = { nome };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Setor",
					"findByNome", parameters, e.toString()));
		}
	}

	public Setor insert(SetorNewDTO setorNewDTO) {
		setorValidation.validateNewSetor(setorNewDTO);
		return save(new Setor(setorNewDTO.getNome()));
	}

	public void update(SetorUpdateDTO setorUpdateDTO, Integer id) {
		Setor setor = new Setor();
		setor = findById(id);
		setorValidation.validateUpdateSetor(setorUpdateDTO, id);

		setor.setNome(setorUpdateDTO.getNome());
		setor.setAtivo(setorUpdateDTO.getAtivo());

		save(setor);
	}

	public Setor save(Setor setor) {
		try {
			return setorRepository.save(setor);
		} catch (DataIntegrityViolationException e) {
			String[] parameters = { "setor" };
			throw new DataIntegrityException(
					textBuilderService.getIntegrityViolationMethodExceptionForMultiParameters("Setor", "save",
							parameters, e.getMostSpecificCause().toString()));
		} catch (Exception e) {
			String[] parameters = { "setor" };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Setor",
					"save", parameters, e.toString()));
		}
	}

	public void deleteById(Integer id) {
		findById(id);
		setorValidation.validateDeleteSetor(id);
		try {
			setorRepository.deleteById(id);
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Setor",
					"deleteById", parameters, e.toString()));
		}
	}
}
