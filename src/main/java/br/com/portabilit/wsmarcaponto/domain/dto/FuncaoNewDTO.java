package br.com.portabilit.wsmarcaponto.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FuncaoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1)
	@NotBlank(message = "É obrigatório inserir o Nome da Função!")
	@Length(max = 50, message = "O Nome da Função deve ter no máximo 50 caracteres!")
	private String nome;

	@ApiModelProperty(position = 2)
	@NotNull(message = "O campo Função Responsável não pode ser null!")
	@Positive(message = "É permitido inserir somente o Id da Função no campo da Função Responsável! Somente valores positivos são aceitos!")
	private Integer funcaoResponsavelId;

	@ApiModelProperty(position = 3)
	@NotNull(message = "O campo Responsável não pode ser null!")
	private Boolean responsavel;

	@ApiModelProperty(position = 4)
	@NotNull(message = "O campo Setor não pode ser null!")
	@Positive(message = "É permitido inserir somente o Id do setor no campo setor_id! Somente valores positivos são aceitos!")
	private Integer setorId;
}
