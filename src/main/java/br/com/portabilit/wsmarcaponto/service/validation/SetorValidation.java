package br.com.portabilit.wsmarcaponto.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Funcao;
import br.com.portabilit.wsmarcaponto.domain.Setor;
import br.com.portabilit.wsmarcaponto.domain.dto.SetorNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.SetorUpdateDTO;
import br.com.portabilit.wsmarcaponto.service.SetorService;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.util.Validator;
import br.com.portabilit.wsmarcaponto.service.validation.util.ValidatorReturn;

@Service
public class SetorValidation {

	@Autowired
	private SetorService setorService;

	@Autowired
	private TextBuilderService textBuilderService;

	protected ValidatorReturn validateSetorById(Integer setorId) {
		Optional<Setor> optSetor = setorService.findByIdForValidation(setorId);
		if (optSetor.isPresent()) {
			if (optSetor.get().getAtivo()) {
				return new ValidatorReturn();
			} else {
				return new ValidatorReturn(textBuilderService.getValidationExceptionForInativeRegister("Setor",
						"setorId", setorId.toString()));
			}
		} else {
			return new ValidatorReturn(textBuilderService.getValidationExceptionForNotFoundRegister("Setor",
					"setorId", setorId.toString()));
		}
	}

	private ValidatorReturn validateSetorByNome(String nome, Integer id) {
		Optional<Setor> optSetor = setorService.findByNome(nome);
		if (id == 0) {
			if (optSetor.isPresent()) {
				return new ValidatorReturn(
						textBuilderService.getValidationExceptionForMultiRegister("Setor", "nome", nome));
			} else {
				return new ValidatorReturn();
			}
		} else {
			if (optSetor.isPresent() && !optSetor.get().getId().equals(id)) {
				return new ValidatorReturn(
						textBuilderService.getValidationExceptionForMultiRegister("Setor", "nome", nome));
			} else {
				return new ValidatorReturn();
			}
		}
	}

	private ValidatorReturn validateIntegrityOnUpdate(Integer id, Boolean ativo) {
		if (ativo == false) {
			Optional<Setor> optSetor = setorService.findByIdForValidation(id);
			List<Funcao> listFuncao = new ArrayList<Funcao>();
			listFuncao = optSetor.get().getFuncoes();
			if (listFuncao.size() > 0) {
				for (Funcao funcao : listFuncao) {
					if (funcao.getAtivo().booleanValue() == true) {
						return new ValidatorReturn(
								textBuilderService.getIntegrityExceptionOnInactivate("Setor", "Função"));
					}
				}
				return new ValidatorReturn();
			} else {
				return new ValidatorReturn();
			}
		} else {
			return new ValidatorReturn();
		}
	}

	private ValidatorReturn validateIntegrityOnDelete(Integer id) {
		Optional<Setor> optSetor = setorService.findByIdForValidation(id);
		if (optSetor.get().getFuncoes().size() > 0) {
			return new ValidatorReturn(textBuilderService.getIntegrityExceptionOnDelete("Setor", "Função"));
		} else {
			return new ValidatorReturn();
		}
	}

	public void validateNewSetor(SetorNewDTO setorNewDTO) {
		Validator validator = new Validator();
		validator.validateOne(validateSetorByNome(setorNewDTO.getNome(), 0));

		validator.validateResult(validator);
	}

	public void validateUpdateSetor(SetorUpdateDTO setorUpdateDTO, Integer id) {
		Validator validator = new Validator();
		validator.validateOne(validateSetorByNome(setorUpdateDTO.getNome(), id));
		validator.validateResult(validator);

		Validator validator02 = new Validator();
		validator02.validateOne(validateIntegrityOnUpdate(id, setorUpdateDTO.getAtivo()));
		validator02.integrityResult(validator02);
	}

	public void validateDeleteSetor(Integer id) {
		Validator validator = new Validator();
		validator.validateOne(validateIntegrityOnDelete(id));

		validator.integrityResult(validator);
	}
}
