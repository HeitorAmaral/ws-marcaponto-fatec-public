package br.com.portabilit.wsmarcaponto.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class ExpedienteNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1, required = true)
	@NotBlank(message = "É obrigatório inserir o Nome do Expediente!")
	private String nome;

	@ApiModelProperty(position = 2, required = true)
	@NotBlank(message = "É obrigatório inserir a Descrição do Expediente!")
	private String descricao;
}