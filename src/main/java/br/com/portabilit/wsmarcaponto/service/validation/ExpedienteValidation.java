package br.com.portabilit.wsmarcaponto.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Colaborador;
import br.com.portabilit.wsmarcaponto.domain.Expediente;
import br.com.portabilit.wsmarcaponto.domain.dto.ExpedienteNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.ExpedienteUpdateDTO;
import br.com.portabilit.wsmarcaponto.service.ExpedienteService;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.util.Validator;
import br.com.portabilit.wsmarcaponto.service.validation.util.ValidatorReturn;

@Service
public class ExpedienteValidation {

	@Autowired
	private ExpedienteService expedienteService;

	@Autowired
	private TextBuilderService textBuilderService;

	protected ValidatorReturn validateExpedienteById(Integer expedienteId) {
		Optional<Expediente> optExpediente = expedienteService.findByIdForValidation(expedienteId);
		if (optExpediente.isPresent()) {
			if (optExpediente.get().getAtivo()) {
				return new ValidatorReturn();
			} else {
				return new ValidatorReturn(textBuilderService.getValidationExceptionForInativeRegister(
						"Expediente", "expedienteId", expedienteId.toString()));
			}
		} else {
			return new ValidatorReturn(textBuilderService.getValidationExceptionForNotFoundRegister("Expediente",
					"expedienteId", expedienteId.toString()));
		}
	}

	private ValidatorReturn validateExpedienteByNome(String nome, Integer id) {
		Optional<Expediente> optExpediente = expedienteService.findByNome(nome);
		if (id == 0) {
			if (optExpediente.isPresent()) {
				return new ValidatorReturn(
						textBuilderService.getValidationExceptionForMultiRegister("Expediente", "nome", nome));
			} else {
				return new ValidatorReturn();
			}
		} else {
			if (optExpediente.isPresent() && !optExpediente.get().getId().equals(id)) {
				return new ValidatorReturn(
						textBuilderService.getValidationExceptionForMultiRegister("Expediente", "nome", nome));
			} else {
				return new ValidatorReturn();
			}
		}
	}

	private ValidatorReturn validateIntegrityOnUpdate(Integer id, Boolean ativo) {
		if (ativo == false) {
			Optional<Expediente> optExpediente = expedienteService.findByIdForValidation(id);
			List<Colaborador> listColaborador = new ArrayList<Colaborador>();
			listColaborador = optExpediente.get().getColaboradores();
			if (listColaborador.size() > 0) {
				for (Colaborador colaborador : listColaborador) {
					if (colaborador.getAtivo().booleanValue() == true) {
						return new ValidatorReturn(
								textBuilderService.getIntegrityExceptionOnInactivate("Expediente", "Colaborador"));
					}
				}
				return new ValidatorReturn();
			} else {
				return new ValidatorReturn();
			}
		} else {
			return new ValidatorReturn();
		}
	}

	private ValidatorReturn validateIntegrityOnDelete(Integer id) {
		Optional<Expediente> optExpediente = expedienteService.findByIdForValidation(id);
		if (optExpediente.get().getColaboradores().size() > 0) {
			return new ValidatorReturn(
					textBuilderService.getIntegrityExceptionOnDelete("Expediente", "Colaborador"));
		} else {
			return new ValidatorReturn();
		}
	}

	public void validateNewExpediente(ExpedienteNewDTO expedienteNewDTO) {
		Validator validator = new Validator();
		validator.validateOne(validateExpedienteByNome(expedienteNewDTO.getNome(), 0));
		validator.validateResult(validator);
	}

	public void validateUpdateExpediente(ExpedienteUpdateDTO expedienteUpdateDTO, Integer id) {
		Validator validator01 = new Validator();
		validator01.validateOne(validateExpedienteByNome(expedienteUpdateDTO.getNome(), id));
		validator01.validateResult(validator01);

		Validator validator02 = new Validator();
		validator02.validateOne(validateIntegrityOnUpdate(id, expedienteUpdateDTO.getAtivo()));
		validator02.integrityResult(validator02);
	}

	public void validateDeleteExpediente(Integer id) {
		Validator validator = new Validator();
		validator.validateOne(validateIntegrityOnDelete(id));

		validator.integrityResult(validator);
	}
}
