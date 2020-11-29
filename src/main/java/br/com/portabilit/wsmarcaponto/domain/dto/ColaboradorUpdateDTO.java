package br.com.portabilit.wsmarcaponto.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColaboradorUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1, required = true)
	@NotBlank(message = "É obrigatório inserir o Nome do Colaborador!")
	private String nome;

	@ApiModelProperty(position = 2, required = true)
	@NotBlank(message = "É obrigatório inserir um Email válido!")
	@Email(message = "É obrigatório inserir um Email válido!")
	private String email;

	@ApiModelProperty(position = 3, required = true)
	@Min(value = 1L, message = "É permitido inserir somente o Id da Função no campo de Função! Somente valores positivos são aceitos!")
	@NotNull(message = "É obrigatório inserir o código da Função")
	private Integer funcaoId;

	@ApiModelProperty(position = 4, required = true)
	@Min(value = 1L, message = "É permitido inserir somente o Id do Expediente no campo de Expediente! Somente valores positivos são aceitos!")
	@NotNull(message = "É obrigatório inserir o código do Expediente")
	private Integer expedienteId;

	@ApiModelProperty(position = 5, required = true)
	@NotNull(message = "O campo Ativo não pode ser null!")
	private Boolean ativo;
}
