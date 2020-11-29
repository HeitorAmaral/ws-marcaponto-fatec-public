package br.com.portabilit.wsmarcaponto.domain.security.dto;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class CredenciaisDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

}
