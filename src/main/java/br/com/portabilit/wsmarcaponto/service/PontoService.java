package br.com.portabilit.wsmarcaponto.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Colaborador;
import br.com.portabilit.wsmarcaponto.domain.Horario;
import br.com.portabilit.wsmarcaponto.domain.Ponto;
import br.com.portabilit.wsmarcaponto.domain.dto.PontoNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.PontoUpdateDTO;
import br.com.portabilit.wsmarcaponto.domain.enums.DiaDaSemana;
import br.com.portabilit.wsmarcaponto.domain.enums.StatusDoPonto;
import br.com.portabilit.wsmarcaponto.domain.enums.TipoDoRegistro;
import br.com.portabilit.wsmarcaponto.repository.PontoRepository;
import br.com.portabilit.wsmarcaponto.service.exception.DataIntegrityException;
import br.com.portabilit.wsmarcaponto.service.exception.GenericException;
import br.com.portabilit.wsmarcaponto.service.exception.ObjectNotFoundException;
import br.com.portabilit.wsmarcaponto.service.security.UsuarioService;
import br.com.portabilit.wsmarcaponto.service.utils.ConverterService;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.PontoValidation;

@Service
public class PontoService {

	@Autowired
	private PontoRepository pontoRepository;

	@Autowired
	private ConverterService converterService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private HorarioService horarioService;

	@Autowired
	private PontoValidation pontoValidation;

	@Autowired
	private TextBuilderService textBuilderService;

	public List<Ponto> findAll() {
		try {
			return pontoRepository.findAll();
		} catch (Exception e) {
			throw new GenericException(
					textBuilderService.getGenericMethodExceptionForNoneParameter("Ponto", "findAll", e.toString()));
		}
	}

	public Ponto findById(Integer id) {
		try {
			return pontoRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException(
					textBuilderService.getValidationExceptionForNotFoundRegister("Ponto", "pontoId", id.toString()));
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Ponto",
					"findById", parameters, e.toString()));
		}
	}

	public List<Ponto> findByColaboradorId(Integer colaboradorId) {
		try {
			return pontoRepository.findByColaboradorId(colaboradorId);
		} catch (Exception e) {
			String[] parameters = { colaboradorId.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Ponto",
					"findByColaboradorId", parameters, e.toString()));
		}
	}

	public List<Ponto> findByDataAndColaboradorId(Date data, Integer colaboradorId) {
		try {
			return pontoRepository.findByDataAndColaboradorId(data, colaboradorId);
		} catch (Exception e) {
			String[] parameters = { data.toString(), colaboradorId.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Ponto",
					"findByDataAndColaboradorId", parameters, e.toString()));
		}
	}

	public List<Ponto> findToApproveByColaboradorId(Integer colaboradorId) {
		try {
			return pontoRepository.findToApproveByColaboradorId(colaboradorId);
		} catch (Exception e) {
			String[] parameters = { colaboradorId.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Ponto",
					"findToApproveByColaboradorId", parameters, e.toString()));
		}
	}

	public List<Ponto> findByColaboradorIdAndStatus(Integer colaboradorId, Integer statusDoPonto) {
		try {
			return pontoRepository.findByColaboradorIdAndStatus(colaboradorId, statusDoPonto);
		} catch (Exception e) {
			String[] parameters = { colaboradorId.toString(), statusDoPonto.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Ponto",
					"findByColaboradorIdAndStatus", parameters, e.toString()));
		}
	}

	public Integer calculateTempoVariavel(Horario horario, Integer horarioPontoParsed) {
		Integer tempoVariavel = horarioPontoParsed - horario.getHorario();
		if (horario.getTipoDoRegistro().equals(TipoDoRegistro.IJ)) {
			if (tempoVariavel <= 0) {// Extra
				tempoVariavel = changePolarity(tempoVariavel); // Fica positivo
				if (tempoVariavel > horario.getToleranciaExtra()) {
					return tempoVariavel;
				} else {
					return 0;
				}
			} else {// Atraso
				if (tempoVariavel > horario.getToleranciaAtraso()) {
					return changePolarity(tempoVariavel);
				} else {
					return 0;
				}
			}
		} else if (horario.getTipoDoRegistro().equals(TipoDoRegistro.FJ)) {
			if (tempoVariavel <= 0) {// Atraso
				tempoVariavel = changePolarity(tempoVariavel); // Fica positivo
				if (tempoVariavel > horario.getToleranciaAtraso()) {// Fora do
					return changePolarity(tempoVariavel);
				} else {
					return 0;
				}
			} else {// Extra
				if (tempoVariavel > horario.getToleranciaExtra()) {
					return tempoVariavel;
				} else {
					return 0;
				}
			}
		}
		return 0;
	}

	public Integer changePolarity(Integer value) {
		return value *= (-1);
	}

	public Ponto updateById(Integer id, PontoUpdateDTO pontoUpdateDTO) {
		Ponto ponto = new Ponto();
		ponto = findById(id);

		pontoValidation.validateUpdatePonto(id, pontoUpdateDTO, ponto.getColaboradorId());

		TipoDoRegistro tipoDoRegistro = TipoDoRegistro.toEnum(pontoUpdateDTO.getTipoDoRegistroId());

		Date dataParsed = converterService.stringToDate(pontoUpdateDTO.getData());
		DiaDaSemana diaDaSemana = converterService.getWeekDayByDate(dataParsed);
		Integer horarioPontoParsed = converterService.hourToMinute(pontoUpdateDTO.getHorario());

		Colaborador colaborador = new Colaborador();
		colaborador = colaboradorService.findById(ponto.getColaboradorId());

		List<Horario> listHorario = new ArrayList<Horario>();
		listHorario = horarioService.findByExpedienteIdAndDiaDaSemanaId(colaborador.getExpedienteId(),
				diaDaSemana.getId());

		Integer tempoVariavel = 0;
		String obs = "Marcação alterada manualmente por " + UsuarioService.authenticated().getUsername() + ". ";

		pontoValidation.validateHorario(listHorario, tipoDoRegistro);

		for (Horario horario : listHorario) {
			if (horario.getTipoDoRegistro().equals(tipoDoRegistro)) {
				tempoVariavel = calculateTempoVariavel(horario, horarioPontoParsed);
				if (tempoVariavel != 0) {
					obs = obs.concat("Horas extras ou negativas a serem análisadas. ");
				}
			}
		}
		ponto.setTipoDoRegistro(tipoDoRegistro);
		ponto.setData(dataParsed);
		ponto.setHorario(horarioPontoParsed);
		ponto.setTempoVariavel(tempoVariavel);
		ponto.setObs(obs);
		ponto.setLocalizacao(pontoUpdateDTO.getLocalizacao());
		ponto.setStatus(StatusDoPonto.AA);
		ponto.setManual(true);
		return save(ponto);
	}

	public Ponto marcarPonto(PontoNewDTO pontoNewDTO) {
		pontoValidation.validateNewPonto(pontoNewDTO);

		Date dataParsed = converterService.stringToDate(pontoNewDTO.getData());
		DiaDaSemana diaDaSemana = converterService.getWeekDayByDate(dataParsed);
		Integer horarioPontoParsed = converterService.hourToMinute(pontoNewDTO.getHorario());

		Colaborador colaborador = new Colaborador();
		colaborador = colaboradorService.findById(pontoNewDTO.getColaboradorId());

		List<Ponto> listPontoByDia = new ArrayList<Ponto>();
		listPontoByDia = findByDataAndColaboradorId(dataParsed, colaborador.getId());

		TipoDoRegistro tipoDoRegistro = TipoDoRegistro.IJ;
		StatusDoPonto statusDoPonto = StatusDoPonto.A;

		Integer tempoVariavel = 0;
		String obs = "";

		if (listPontoByDia.size() > 0 && listPontoByDia.size() < 4) {
			List<TipoDoRegistro> listTipoDePontoByDia = new ArrayList<TipoDoRegistro>();
			for (Ponto ponto : listPontoByDia) {
				listTipoDePontoByDia.add(ponto.getTipoDoRegistro());
			}
			if (listTipoDePontoByDia.contains(TipoDoRegistro.FJ)) {
				tipoDoRegistro = TipoDoRegistro.XX;
			} else if (listTipoDePontoByDia.contains(TipoDoRegistro.FI)) {
				tipoDoRegistro = TipoDoRegistro.FJ;
			} else if (listTipoDePontoByDia.contains(TipoDoRegistro.II)) {
				tipoDoRegistro = TipoDoRegistro.FI;
			} else {
				tipoDoRegistro = TipoDoRegistro.II;
			}
		}

		List<Horario> listHorario = new ArrayList<Horario>();
		listHorario = horarioService.findByExpedienteIdAndDiaDaSemanaId(colaborador.getExpedienteId(),
				diaDaSemana.getId());

		pontoValidation.validateHorario(listHorario, tipoDoRegistro);

		for (Horario horario : listHorario) {
			if (horario.getTipoDoRegistro().equals(tipoDoRegistro)) {
				tempoVariavel = calculateTempoVariavel(horario, horarioPontoParsed);
				if (tempoVariavel != 0) {
					statusDoPonto = StatusDoPonto.AA;
					obs = obs.concat("Horas extras ou negativas a serem análisadas.");
				}
			}
		}

		if (pontoNewDTO.getManual()) {
			statusDoPonto = StatusDoPonto.AA;
		}

		return save(new Ponto(colaborador.getId(), dataParsed, horarioPontoParsed, tempoVariavel,
				pontoNewDTO.getLocalizacao(), tipoDoRegistro, statusDoPonto, pontoNewDTO.getManual(), obs));
	}

	public void approveById(Integer id) {
		Ponto ponto = new Ponto();
		ponto = findById(id);
		ponto.setStatus(StatusDoPonto.A);
		save(ponto);
	}

	public void disapproveById(Integer id, String obs) {
		Ponto ponto = new Ponto();
		ponto = findById(id);
		ponto.setStatus(StatusDoPonto.R);
		ponto.setObs(obs.trim());
		save(ponto);
	}

	public Ponto save(Ponto ponto) {
		try {
			return pontoRepository.save(ponto);
		} catch (DataIntegrityViolationException e) {
			String[] parameters = { "ponto" };
			throw new DataIntegrityException(textBuilderService.getIntegrityViolationMethodExceptionForMultiParameters(
					"Ponto", "save", parameters, e.getMostSpecificCause().toString()));
		} catch (Exception e) {
			String[] parameters = { "ponto" };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Ponto", "save",
					parameters, e.toString()));
		}
	}

	public void deleteById(Integer id) {
		findById(id);
		try {
			pontoRepository.deleteById(id);
		} catch (Exception e) {
			String[] parameters = { id.toString() };
			throw new GenericException(textBuilderService.getGenericMethodExceptionForMultiParameters("Ponto",
					"deleteById", parameters, e.toString()));
		}
	}
}
