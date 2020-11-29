package br.com.portabilit.wsmarcaponto.service.validation.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidatorReturn {

	private Boolean validated;
	private String message;

	public ValidatorReturn() {
		this.validated = true;
		this.message = null;
	}

	public ValidatorReturn(String message) {
		this.validated = false;
		this.message = message;
	}

}
