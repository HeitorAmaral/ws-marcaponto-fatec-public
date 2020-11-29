package br.com.portabilit.wsmarcaponto.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.portabilit.wsmarcaponto.domain.enums.StatusDoPonto;
import br.com.portabilit.wsmarcaponto.domain.enums.TipoDoRegistro;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "Entidade Ponto")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class Ponto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ApiModelProperty(position = 2)
	@Column(nullable = false)
	private Integer colaboradorId;

	@ApiModelProperty(position = 3)
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Brazil/East")
	private Date data;

	@ApiModelProperty(position = 4)
	@Column(nullable = false)
	private Integer horario;

	@ApiModelProperty(position = 5)
	@Column(nullable = false)
	private Integer tempoVariavel;

	@ApiModelProperty(position = 6)
	private String localizacao;

	@ApiModelProperty(position = 7)
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private TipoDoRegistro tipoDoRegistro;

	@ApiModelProperty(position = 8)
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private StatusDoPonto status;

	@ApiModelProperty(position = 9)
	@Column(nullable = false)
	private Boolean manual;

	@ApiModelProperty(position = 10)
	private String obs;

	public Ponto(Integer colaboradorId, Date data, Integer horario, Integer tempoVariavel, String localizacao,
			TipoDoRegistro tipoDoRegistro, StatusDoPonto status, Boolean manual, String obs) {
		this.colaboradorId = colaboradorId;
		this.data = data;
		this.horario = horario;
		this.tempoVariavel = tempoVariavel;
		this.localizacao = localizacao;
		this.tipoDoRegistro = tipoDoRegistro;
		this.status = status;
		this.manual = manual;
		this.obs = obs;
	}
}
