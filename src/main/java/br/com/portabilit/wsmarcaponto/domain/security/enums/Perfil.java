package br.com.portabilit.wsmarcaponto.domain.security.enums;

import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"), RESPONSAVEL(2, "ROLE_RESPONSAVEL"), COLABORADOR(3, "ROLE_COLABORADOR");

	private Integer id;
	private String nome;

	private Perfil(Integer id, String nome) {
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

	public static Perfil toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		for (Perfil perfil : Perfil.values()) {
			if (id.equals(perfil.getId())) {
				return perfil;
			}
		}
		TextBuilderService textBuilderService = new TextBuilderService();
		throw new IllegalArgumentException(
				textBuilderService.getValidationExceptionForNotFoundRegister("Perfil", "id", id.toString()));
	}

}