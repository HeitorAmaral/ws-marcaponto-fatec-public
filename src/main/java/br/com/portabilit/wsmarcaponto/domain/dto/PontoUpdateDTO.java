package br.com.portabilit.wsmarcaponto.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PontoUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1, required = true, notes = "Deve ser preenchido conforme o padrão DD/MM/AAAA.")
	@NotBlank(message = "É obrigatório inserir a Data de marcação do Ponto!")
	@Pattern(regexp = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$", message = "É obrigatório inserir a Data no padrão DD/MM/AAAA")
	private String data;

	@ApiModelProperty(position = 2, required = true, example = "12:00", notes = "Deve ser preenchido apenas a HORA e o MINUTO, conforme o padrão HH:MM")
	@NotBlank(message = "É obrigatório inserir a HORA base do PARÂMETRO DE PONTO!")
	@Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "É obrigatório inserir a HORA base do PARÂMETRO DE PONTO no padrão HH:MM")
	private String horario;

	@ApiModelProperty(position = 3, required = true, example = "1", allowableValues = "range[0, 3]", notes = "Deve ser preenchido com o ID do TIPO DO REGISTRO.")
	@NotNull(message = "É obrigatório inserir o ID do TIPO DO REGISTRO!")
	@Min(value = 0L, message = "Somente são permitidos valores dentro do intervalo de 0 á 3!")
	@Max(value = 3L, message = "Somente são permitidos valores dentro do intervalo de 0 á 3!")
	private Integer tipoDoRegistroId;

	@ApiModelProperty(position = 4)
	private String localizacao;
}
