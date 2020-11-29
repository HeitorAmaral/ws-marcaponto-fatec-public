package br.com.portabilit.wsmarcaponto.service.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Colaborador;
import br.com.portabilit.wsmarcaponto.domain.dto.ColaboradorNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.ColaboradorUpdateDTO;
import br.com.portabilit.wsmarcaponto.service.ColaboradorService;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.util.Validator;
import br.com.portabilit.wsmarcaponto.service.validation.util.ValidatorReturn;

@Service
public class ColaboradorValidation {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FuncaoValidation funcaoValidation;

	@Autowired
	private ExpedienteValidation expedienteValidation;

	@Autowired
	private TextBuilderService textBuilderService;

	protected ValidatorReturn validateColaboradorById(Integer colaboradorId) {
		Optional<Colaborador> optColaborador = colaboradorService.findByIdForValidation(colaboradorId);
		if (optColaborador.isPresent()) {
			if (optColaborador.get().getAtivo()) {
				return new ValidatorReturn();
			} else {
				return new ValidatorReturn(textBuilderService.getValidationExceptionForInativeRegister(
						"Colaborador", "colaboradorId", colaboradorId.toString()));
			}
		} else {
			return new ValidatorReturn(textBuilderService.getValidationExceptionForNotFoundRegister("Colaborador",
					"colaboradorId", colaboradorId.toString()));
		}
	}

	private ValidatorReturn validateColaboradorByEmail(String email, Integer id) {
		Optional<Colaborador> colaborador = colaboradorService.findByEmail(email);
		if (id == 0) {
			if (colaborador.isPresent()) {
				if (colaborador.get().getAtivo()) {
					return new ValidatorReturn(textBuilderService
							.getValidationExceptionForMultiRegister("Colaborador", "email", email.toString()));
				} else {
					return new ValidatorReturn();
				}
			} else {
				return new ValidatorReturn();
			}
		} else {
			if (colaborador.isPresent()) {
				if (colaborador.get().getAtivo() && !colaborador.get().getId().equals(id)) {
					return new ValidatorReturn(textBuilderService
							.getValidationExceptionForMultiRegister("Colaborador", "email", email.toString()));
				} else {
					return new ValidatorReturn();
				}
			} else {
				return new ValidatorReturn();
			}
		}
	}

	public void validateNewColaborador(ColaboradorNewDTO colaboradorNewDTO) {
		Validator validator = new Validator();
		validator.validateOne(funcaoValidation.validateFuncaoById(colaboradorNewDTO.getFuncaoId()));
		validator.validateOne(expedienteValidation.validateExpedienteById(colaboradorNewDTO.getExpedienteId()));

		if (validator.getListFalse().size() <= 0) {
			validator.validateOne(validateColaboradorByEmail(colaboradorNewDTO.getEmail(), 0));
		}

		validator.validateResult(validator);
	}

	public void validateUpdateColaborador(ColaboradorUpdateDTO colaboradorAlterDTO, Integer id) {
		Validator validator = new Validator();
		validator.validateOne(funcaoValidation.validateFuncaoById(colaboradorAlterDTO.getFuncaoId()));
		validator.validateOne(expedienteValidation.validateExpedienteById(colaboradorAlterDTO.getExpedienteId()));

		if (validator.getListFalse().size() <= 0) {
			validator.validateOne(validateColaboradorByEmail(colaboradorAlterDTO.getEmail(), id));
		}

		validator.validateResult(validator);
	}
}
