package br.com.portabilit.wsmarcaponto.domain.enums;

import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;

public enum TipoDoRegistro {

	IJ(0, "Início da Jornada"), II(1, "Início do Intervalo"), FI(2, "Fim do Intervalo"), FJ(3, "Fim da Jornada"),
	XX(4, "Indefinido");

	private Integer id;
	private String nome;

	private TipoDoRegistro(Integer id, String nome) {
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

	public static TipoDoRegistro toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		for (TipoDoRegistro tipoRegistro : TipoDoRegistro.values()) {
			if (id.equals(tipoRegistro.getId())) {
				return tipoRegistro;
			}
		}
		TextBuilderService textBuilderService = new TextBuilderService();
		throw new IllegalArgumentException(textBuilderService
				.getValidationExceptionForNotFoundRegister("Tipo do Registro", "id", id.toString()));
	}

}