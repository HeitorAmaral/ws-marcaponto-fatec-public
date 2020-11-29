package br.com.portabilit.wsmarcaponto.service.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Horario;
import br.com.portabilit.wsmarcaponto.domain.dto.HorarioAlterDTO;
import br.com.portabilit.wsmarcaponto.service.HorarioService;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.util.Validator;
import br.com.portabilit.wsmarcaponto.service.validation.util.ValidatorReturn;

@Service
public class HorarioValidation {

	@Autowired
	private HorarioService horarioService;

	@Autowired
	private ExpedienteValidation expedienteValidation;

	@Autowired
	private DiaDaSemanaValidation diaDaSemanaValidation;

	@Autowired
	private TipoDoRegistroValidation tipoDoRegistroValidation;

	@Autowired
	private TextBuilderService textBuilderService;

	private ValidatorReturn validateHorarioByExpedienteIdAndDiaDaSemanaIdAndTipoDoRegistroId(Integer expedienteId,
			Integer diaDaSemanaId, Integer tipoDoRegistroId, Integer id) {
		Optional<Horario> horario = horarioService.findByExpedienteIdAndDiaDaSemanaIdAndTipoDoRegistroId(expedienteId,
				diaDaSemanaId, tipoDoRegistroId);
		if (id == 0) {
			if (horario.isPresent()) {
				String[] parametersName = { "expedienteId", "diaDaSemanaId", "tipoDoRegistroId" };
				String[] parametersValue = { expedienteId.toString(), diaDaSemanaId.toString(),
						tipoDoRegistroId.toString() };
				return new ValidatorReturn(
						textBuilderService.getValidationExceptionForMultiRegisterAndMultiParameters("Horário",
								parametersName, parametersValue));
			} else {
				return new ValidatorReturn();
			}
		} else {
			if (horario.isPresent() && !horario.get().getId().equals(id)) {
				String[] parametersName = { "expedienteId", "diaDaSemanaId", "tipoDoRegistroId" };
				String[] parametersValue = { expedienteId.toString(), diaDaSemanaId.toString(),
						tipoDoRegistroId.toString() };
				return new ValidatorReturn(
						textBuilderService.getValidationExceptionForMultiRegisterAndMultiParameters("Horário",
								parametersName, parametersValue));
			} else {
				return new ValidatorReturn();
			}
		}
	}

	public void validateNewHorario(HorarioAlterDTO horarioAlterDTO) {
		Validator validator = new Validator();
		validator.validateOne(expedienteValidation.validateExpedienteById(horarioAlterDTO.getExpedienteId()));
		validator.validateOne(diaDaSemanaValidation.validateDiaDaSemanaById(horarioAlterDTO.getDiaDaSemanaId()));
		validator.validateOne(
				tipoDoRegistroValidation.validateTipoDoRegistroById(horarioAlterDTO.getTipoDoRegistroId()));
		if (validator.getListFalse().size() <= 0) {
			validator.validateOne(
					validateHorarioByExpedienteIdAndDiaDaSemanaIdAndTipoDoRegistroId(horarioAlterDTO.getExpedienteId(),
							horarioAlterDTO.getDiaDaSemanaId(), horarioAlterDTO.getTipoDoRegistroId(), 0));
		}

		validator.validateResult(validator);
	}

	public void validateUpdateHorario(HorarioAlterDTO horarioAlterDTO, Integer id) {
		Validator validator = new Validator();
		validator.validateOne(expedienteValidation.validateExpedienteById(horarioAlterDTO.getExpedienteId()));
		validator.validateOne(diaDaSemanaValidation.validateDiaDaSemanaById(horarioAlterDTO.getDiaDaSemanaId()));
		validator.validateOne(
				tipoDoRegistroValidation.validateTipoDoRegistroById(horarioAlterDTO.getTipoDoRegistroId()));
		if (validator.getListFalse().size() <= 0) {
			validator.validateOne(
					validateHorarioByExpedienteIdAndDiaDaSemanaIdAndTipoDoRegistroId(horarioAlterDTO.getExpedienteId(),
							horarioAlterDTO.getDiaDaSemanaId(), horarioAlterDTO.getTipoDoRegistroId(), id));
		}

		validator.validateResult(validator);
	}

}
