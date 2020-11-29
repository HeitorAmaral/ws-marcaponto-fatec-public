package br.com.portabilit.wsmarcaponto.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "Entidade Setor")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class Setor implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ApiModelProperty(position = 2)
	@Column(nullable = false, length = 50)
	private String nome;

	@ApiModelProperty(position = 3)
	@Column(nullable = false)
	private Boolean ativo;

	@JsonIgnore
	@OneToMany
	@JoinColumn(name = "setorId")
	private List<Funcao> funcoes;

	public Setor(String nome) {
		this.nome = nome;
		this.ativo = true;
	}
}
