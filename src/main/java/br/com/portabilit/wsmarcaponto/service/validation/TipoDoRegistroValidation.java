package br.com.portabilit.wsmarcaponto.service.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.enums.TipoDoRegistro;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.util.ValidatorReturn;

@Service
public class TipoDoRegistroValidation {

	@Autowired
	private TextBuilderService textBuilderService;

	protected ValidatorReturn validateTipoDoRegistroById(Integer tipoDoRegistroId) {
		List<Integer> listId = new ArrayList<Integer>();
		for (TipoDoRegistro tipoDoRegistro : TipoDoRegistro.values()) {
			listId.add(tipoDoRegistro.getId());
		}
		if (listId.contains(tipoDoRegistroId)) {
			return new ValidatorReturn();
		} else {
			return new ValidatorReturn(textBuilderService.getValidationExceptionForNotFoundRegister(
					"Tipo do Registro", "tipoDoRegistroId", tipoDoRegistroId.toString()));
		}
	}
}
