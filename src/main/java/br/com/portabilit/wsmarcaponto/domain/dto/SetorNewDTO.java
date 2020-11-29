package br.com.portabilit.wsmarcaponto.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SetorNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1, required = true, notes = "Deve ser preenchido e possuir no máximo 50 caracteres.")
	@NotBlank(message = "É obrigatório inserir o Nome do Setor!")
	@Length(max = 50, message = "O Nome do Setor deve ter no máximo 50 caracteres!")
	private String nome;
}