package br.com.portabilit.wsmarcaponto.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PontoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1, required = true, example = "1", allowableValues = "range[1, infinity]", notes = "Deve ser preenchido com o ID do EXPEDIENTE.")
	@NotNull(message = "É obrigatório inserir o ID do EXPEDIENTE!")
	@Positive(message = "Somente são permitidos valores positivos!")
	private Integer colaboradorId;

	@ApiModelProperty(position = 2, required = true, notes = "Deve ser preenchido conforme o padrão DD/MM/AAAA.")
	@NotBlank(message = "É obrigatório inserir a Data de marcação do Ponto!")
	@Pattern(regexp = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$", message = "É obrigatório inserir a Data no padrão DD/MM/AAAA")
	private String data;

	@ApiModelProperty(position = 3, required = true, example = "12:00", notes = "Deve ser preenchido apenas a HORA e o MINUTO, conforme o padrão HH:MM")
	@NotBlank(message = "É obrigatório inserir a HORA base do PARÂMETRO DE PONTO!")
	@Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "É obrigatório inserir a HORA base do PARÂMETRO DE PONTO no padrão HH:MM")
	private String horario;

	@ApiModelProperty(position = 4)
	private String localizacao;

	@ApiModelProperty(position = 5)
	private Boolean manual;
}
