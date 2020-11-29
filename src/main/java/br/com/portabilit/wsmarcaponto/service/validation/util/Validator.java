package br.com.portabilit.wsmarcaponto.service.validation.util;

import java.util.ArrayList;
import java.util.List;

import br.com.portabilit.wsmarcaponto.service.exception.ConstraintViolationException;
import br.com.portabilit.wsmarcaponto.service.exception.GenericValidationException;
import lombok.Getter;

@Getter
public class Validator {

	private List<Boolean> listTrue = new ArrayList<Boolean>();
	private List<Boolean> listFalse = new ArrayList<Boolean>();
	private List<String> listMessage = new ArrayList<String>();

	public void validateOne(ValidatorReturn validatorReturn) {
		Boolean b = validatorReturn.getValidated();
		String m = validatorReturn.getMessage();
		if (b == true) {
			listTrue.add(b);
		} else {
			listFalse.add(b);
			listMessage.add(m);
		}
	}

	public void validateList(List<ValidatorReturn> listValidatorReturn) {
		Boolean b = true;
		String m = null;
		for (ValidatorReturn validatorReturn : listValidatorReturn) {
			b = validatorReturn.getValidated();
			m = validatorReturn.getMessage();

			if (b == true) {
				listTrue.add(b);
			} else {
				listFalse.add(b);
				listMessage.add(m);
			}
		}
	}

	public void validateResult(Validator validator) {
		if (validator.getListFalse().size() > 0) {
			throw new GenericValidationException("DTO com divergência as regras de negócio estabelecidas.",
					validator.getListMessage());
		}
	}

	public void integrityResult(Validator validator) {
		if (validator.getListFalse().size() > 0) {
			throw new ConstraintViolationException("O objeto possui relacionamentos em outras entidades.",
					validator.getListMessage());
		}
	}
}
