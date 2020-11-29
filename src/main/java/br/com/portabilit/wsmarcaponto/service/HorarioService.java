package br.com.portabilit.wsmarcaponto.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Horario;
import br.com.portabilit.wsmarcaponto.domain.dto.HorarioAlterDTO;
import br.com.portabilit.wsmarcaponto.domain.enums.DiaDaSemana;
import br.com.portabilit.wsmarcaponto.domain.enums.TipoDoRegistro;
import br.com.portabilit.wsmarcaponto.repository.HorarioRepository;
import br.com.portabilit.wsmarcaponto.service.exception.DataIntegrityException;
import br.com.portabilit.wsmarcaponto.service.exception.GenericException;
import br.com.portabilit.wsmarcaponto.service.exception.ObjectNotFoundException;
import br.com.portabilit.wsmarcaponto.service.utils.ConverterService;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.HorarioValidation;

@Service
public class HorarioService {

	@Autowired
	private HorarioRepository horarioRepository;

	@Autowired
	private HorarioValidation horarioValidation;

	@Autowired
	private TextBuilderService textBuilderService;

	@Autowired
	private ConverterService converterService;

	public List<Horario> findAll() {
		try {
			return horarioRepository.findAll();
		} catch (Exception e) {
			throw new GenericException(textBuilderService.getGenericMethodExceptionForNoneParameter("Horario",
					"findAll", e.toString()));
		}
	}

	public Horario findById(Integer id) {
		try {
			return horarioRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException(textBuilderService
					.getValidationExceptionForNotFoundRegister("Horario", "horarioId", id.toString()));
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Horario",
					"findById", parameters, e.toString()));
		}
	}

	public Optional<Horario> findByIdForValidation(Integer id) {
		try {
			return horarioRepository.findById(id);
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Horario",
					"findByIdForValidation", parameters, e.toString()));
		}
	}

	public Optional<Horario> findByExpedienteIdAndDiaDaSemanaIdAndTipoDoRegistroId(Integer expedienteId,
			Integer diaDaSemanaId, Integer tipoDoRegistroId) {
		try {
			return horarioRepository.findByExpedienteIdAndDiaDaSemanaIdAndTipoDoRegistroId(expedienteId, diaDaSemanaId,
					tipoDoRegistroId);
		} catch (Exception e) {
			String[] parameters = { "expedienteId", "diaDaSemanaId", "tipoDoRegistroId" };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Horario",
					"findByExpedienteIdAndDiaDaSemanaIdAndTipoDoRegistroId", parameters, e.toString()));
		}
	}

	public List<Horario> findByExpedienteIdAndDiaDaSemanaId(Integer expedienteId, Integer diaDaSemanaId) {
		try {
			return horarioRepository.findByExpedienteIdAndDiaDaSemanaId(expedienteId, diaDaSemanaId);
		} catch (Exception e) {
			String[] parameters = { expedienteId.toString(), diaDaSemanaId.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Horario",
					"findByExpedienteIdAndDiaDaSemanaId", parameters, e.toString()));
		}
	}

	public Horario insert(HorarioAlterDTO horarioAlterDTO) {
		horarioValidation.validateNewHorario(horarioAlterDTO);
		return save(new Horario(horarioAlterDTO.getExpedienteId(), horarioAlterDTO.getDiaDaSemanaId(),
				horarioAlterDTO.getTipoDoRegistroId(), horarioAlterDTO.getHorario(),
				horarioAlterDTO.getToleranciaExtra(), horarioAlterDTO.getToleranciaAtraso()));
	}

	public void update(HorarioAlterDTO horarioAlterDTO, Integer id) {
		Horario horario = new Horario();
		horario = findById(id);
		horarioValidation.validateUpdateHorario(horarioAlterDTO, id);

		horario.setExpedienteId(horarioAlterDTO.getExpedienteId());
		horario.setDiaDaSemana(DiaDaSemana.toEnum(horarioAlterDTO.getDiaDaSemanaId()));
		horario.setTipoDoRegistro(TipoDoRegistro.toEnum(horarioAlterDTO.getTipoDoRegistroId()));
		horario.setHorario(converterService.hourToMinute(horarioAlterDTO.getHorario()));
		horario.setToleranciaExtra(converterService.hourToMinute(horarioAlterDTO.getToleranciaExtra()));
		horario.setToleranciaAtraso(converterService.hourToMinute(horarioAlterDTO.getToleranciaAtraso()));

		save(horario);
	}

	public Horario save(Horario horario) {
		try {
			return horarioRepository.save(horario);
		} catch (DataIntegrityViolationException e) {
			String[] parameters = { "horario" };
			throw new DataIntegrityException(
					textBuilderService.getIntegrityViolationMethodExceptionForMultiParameters("Horario", "save",
							parameters, e.getMostSpecificCause().toString()));
		} catch (Exception e) {
			String[] parameters = { "horario" };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Horario",
					"save", parameters, e.toString()));
		}
	}

	public void deleteById(Integer id) {
		findById(id);
		try {
			horarioRepository.deleteById(id);
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Horario",
					"deleteById", parameters, e.toString()));
		}
	}
}
