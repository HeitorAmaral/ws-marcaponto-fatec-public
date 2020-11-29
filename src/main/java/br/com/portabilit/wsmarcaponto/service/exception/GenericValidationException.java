package br.com.portabilit.wsmarcaponto.service.exception;

import java.util.List;

public class GenericValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private List<String> listaErrors;

	public GenericValidationException(String msg) {
		super(msg);
	}

	public GenericValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public GenericValidationException(String msg, Throwable cause, List<String> errors) {
		super(msg, cause);
		this.listaErrors = errors;
	}

	public GenericValidationException(String msg, List<String> errors) {
		super(msg);
		this.listaErrors = errors;
	}

	public List<String> getListaErrors() {
		return listaErrors;
	}

	public void setListaErrors(List<String> listaErrors) {
		this.listaErrors = listaErrors;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
