package br.com.portabilit.wsmarcaponto.domain.dto;

import java.io.Serializable;

import br.com.portabilit.wsmarcaponto.domain.Horario;
import br.com.portabilit.wsmarcaponto.service.utils.ConverterService;
import lombok.Getter;

@Getter
public class HorarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer expedienteId;

	private String diaDaSemana;

	private String tipoRegistro;

	private String horario;

	private String toleranciaExtra;

	private String toleranciaAtraso;

	public HorarioDTO(Horario horario) {
		ConverterService converterService = new ConverterService();
		this.id = horario.getId();
		this.expedienteId = horario.getExpedienteId();
		this.diaDaSemana = horario.getDiaDaSemana().getNome();
		this.tipoRegistro = horario.getTipoDoRegistro().getNome();
		this.horario = converterService.minuteToHour(horario.getHorario());
		this.toleranciaExtra = converterService.minuteToHour(horario.getToleranciaExtra());
		this.toleranciaAtraso = converterService.minuteToHour(horario.getToleranciaAtraso());
	}
}
