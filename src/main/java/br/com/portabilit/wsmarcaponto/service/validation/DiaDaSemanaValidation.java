package br.com.portabilit.wsmarcaponto.service.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.enums.DiaDaSemana;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.util.ValidatorReturn;

@Service
public class DiaDaSemanaValidation {

	@Autowired
	private TextBuilderService textBuilderService;

	protected ValidatorReturn validateDiaDaSemanaById(Integer diaDaSemanaId) {
		List<Integer> listId = new ArrayList<Integer>();
		for (DiaDaSemana diaDaSemana : DiaDaSemana.values()) {
			listId.add(diaDaSemana.getId());
		}
		if (listId.contains(diaDaSemanaId)) {
			return new ValidatorReturn();
		} else {
			return new ValidatorReturn(textBuilderService.getValidationExceptionForNotFoundRegister(
					"Dia da Semana", "diaDaSemanaId", diaDaSemanaId.toString()));
		}
	}
}
