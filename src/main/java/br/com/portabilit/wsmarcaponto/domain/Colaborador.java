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

@ApiModel(description = "Entidade Colaborador")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class Colaborador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(position = 1)
	private Integer id;

	@ApiModelProperty(position = 2)
	@Column(nullable = false)
	private String nome;

	@ApiModelProperty(position = 3)
	@Column(nullable = true, unique = true)
	private String email;

	@ApiModelProperty(position = 4)
	@Column(nullable = false)
	private Integer funcaoId;

	@ApiModelProperty(position = 5)
	@Column(nullable = false)
	private Integer expedienteId;

	@Column(nullable = false)
	@ApiModelProperty(position = 6)
	private Boolean ativo;

	@JsonIgnore
	@OneToMany
	@JoinColumn(name = "colaboradorId")
	private List<Ponto> pontos;

	public Colaborador(String nome, String email, Integer funcaoId, Integer expedienteId, Boolean ativo) {
		this.nome = nome;
		this.email = email;
		this.funcaoId = funcaoId;
		this.expedienteId = expedienteId;
		this.ativo = ativo;
	}
}
