package br.com.portabilit.wsmarcaponto.domain.enums;

import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;

public enum DiaDaSemana {

	DOM(0, "Domingo"), SEG(1, "Segunda-Feira"), TER(2, "Terça-Feira"), QUA(3, "Quarta-Feira"), QUI(4, "Quinta-Feira"),
	SEX(5, "Sexta-Feira"), SAB(6, "Sábado");

	private Integer id;
	private String nome;

	private DiaDaSemana(Integer id, String nome) {
		this.setId(id);
		this.setNome(nome);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static DiaDaSemana toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		for (DiaDaSemana diaDaSemana : DiaDaSemana.values()) {
			if (id.equals(diaDaSemana.getId())) {
				return diaDaSemana;
			}
		}
		TextBuilderService textBuilderService = new TextBuilderService();
		throw new IllegalArgumentException(textBuilderService
				.getValidationExceptionForNotFoundRegister("Dia da Semana", "id", id.toString()));
	}
}
