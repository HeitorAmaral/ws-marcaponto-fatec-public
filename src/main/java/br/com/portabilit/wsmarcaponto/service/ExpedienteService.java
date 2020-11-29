package br.com.portabilit.wsmarcaponto.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Expediente;
import br.com.portabilit.wsmarcaponto.domain.dto.ExpedienteNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.ExpedienteUpdateDTO;
import br.com.portabilit.wsmarcaponto.repository.ExpedienteRepository;
import br.com.portabilit.wsmarcaponto.service.exception.DataIntegrityException;
import br.com.portabilit.wsmarcaponto.service.exception.GenericException;
import br.com.portabilit.wsmarcaponto.service.exception.ObjectNotFoundException;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.ExpedienteValidation;

@Service
public class ExpedienteService {

	@Autowired
	private ExpedienteRepository expedienteRepository;

	@Autowired
	private ExpedienteValidation expedienteValidation;

	@Autowired
	private TextBuilderService textBuilderService;

	public List<Expediente> findAll() {
		try {
			return expedienteRepository.findAll();
		} catch (Exception e) {
			throw new GenericException(textBuilderService.getGenericMethodExceptionForNoneParameter("Expediente",
					"findAll", e.toString()));
		}
	}

	public Expediente findById(Integer id) {
		try {
			return expedienteRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException(textBuilderService
					.getValidationExceptionForNotFoundRegister("Expediente", "expedienteId", id.toString()));
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Expediente",
					"findById", parameters, e.toString()));
		}
	}

	public Optional<Expediente> findByIdForValidation(Integer id) {
		try {
			return expedienteRepository.findById(id);
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Expediente",
					"findByIdForValidation", parameters, e.toString()));
		}
	}

	public List<Expediente> findByAtivo(Boolean ativo) {
		try {
			return expedienteRepository.findByAtivo(ativo);
		} catch (Exception e) {
			String[] parameters = { ativo.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Expediente",
					"findByAtivo", parameters, e.toString()));
		}
	}

	public Optional<Expediente> findByNome(String nome) {
		try {
			return expedienteRepository.findByNome(nome);
		} catch (Exception e) {
			String[] parameters = { nome };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Expediente",
					"findByNome", parameters, e.toString()));
		}
	}

	public Expediente insert(ExpedienteNewDTO expedienteNewDTO) {
		expedienteValidation.validateNewExpediente(expedienteNewDTO);
		return save(new Expediente(expedienteNewDTO.getNome(), expedienteNewDTO.getDescricao()));
	}

	public void update(ExpedienteUpdateDTO expedienteUpdateDTO, Integer id) {
		Expediente expediente = new Expediente();
		expediente = findById(id);
		expedienteValidation.validateUpdateExpediente(expedienteUpdateDTO, id);

		expediente.setNome(expedienteUpdateDTO.getNome());
		expediente.setDescricao(expediente.getDescricao());
		expediente.setAtivo(expedienteUpdateDTO.getAtivo());

		save(expediente);
	}

	public Expediente save(Expediente expediente) {
		try {
			return expedienteRepository.save(expediente);
		} catch (DataIntegrityViolationException e) {
			String[] parameters = { "expediente" };
			throw new DataIntegrityException(
					textBuilderService.getIntegrityViolationMethodExceptionForMultiParameters("Expediente", "save",
							parameters, e.getMostSpecificCause().toString()));
		} catch (Exception e) {
			String[] parameters = { "expediente" };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Expediente",
					"save", parameters, e.toString()));
		}
	}

	public void deleteById(Integer id) {
		findById(id);
		expedienteValidation.validateDeleteExpediente(id);
		try {
			expedienteRepository.deleteById(id);
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Expediente",
					"deleteById", parameters, e.toString()));
		}
	}
}
