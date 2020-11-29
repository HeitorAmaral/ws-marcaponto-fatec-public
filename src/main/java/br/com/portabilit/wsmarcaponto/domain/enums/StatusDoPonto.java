package br.com.portabilit.wsmarcaponto.domain.enums;

import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;

public enum StatusDoPonto {

	AA(0, "Aguardando Aprovação"), A(1, "Aprovado"), R(2, "Retornado");

	private Integer id;
	private String nome;

	private StatusDoPonto(Integer id, String nome) {
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

	public static StatusDoPonto toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		for (StatusDoPonto tipoRegistro : StatusDoPonto.values()) {
			if (id.equals(tipoRegistro.getId())) {
				return tipoRegistro;
			}
		}
		TextBuilderService textBuilderService = new TextBuilderService();
		throw new IllegalArgumentException(
				textBuilderService.getValidationExceptionForNotFoundRegister("Status do Ponto", "id", id.toString()));
	}

}