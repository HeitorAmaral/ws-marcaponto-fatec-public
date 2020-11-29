package br.com.portabilit.wsmarcaponto.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.portabilit.wsmarcaponto.domain.enums.DiaDaSemana;
import br.com.portabilit.wsmarcaponto.domain.enums.TipoDoRegistro;
import br.com.portabilit.wsmarcaponto.service.utils.ConverterService;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class Horario implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ApiModelProperty(position = 2)
	@Column(nullable = false)
	private Integer expedienteId;

	@ApiModelProperty(position = 3)
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private DiaDaSemana diaDaSemana;

	@ApiModelProperty(position = 4)
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private TipoDoRegistro tipoDoRegistro;

	@ApiModelProperty(position = 6)
	@Column(nullable = false)
	private Integer horario;

	@ApiModelProperty(position = 8)
	@Column(nullable = false)
	private Integer toleranciaExtra;

	@ApiModelProperty(position = 8)
	@Column(nullable = false)
	private Integer toleranciaAtraso;

	public Horario(Integer expedienteId, Integer diaDaSemanaId, Integer tipoDoRegistroId, String horario,
			String toleranciaExtra, String toleranciaAtraso) {
		ConverterService converterService = new ConverterService();
		this.expedienteId = expedienteId;
		this.diaDaSemana = DiaDaSemana.toEnum(diaDaSemanaId);
		this.tipoDoRegistro = TipoDoRegistro.toEnum(tipoDoRegistroId);
		this.horario = converterService.hourToMinute(horario);
		this.toleranciaExtra = converterService.hourToMinute(toleranciaExtra);
		this.toleranciaAtraso = converterService.hourToMinute(toleranciaAtraso);
	}
}
