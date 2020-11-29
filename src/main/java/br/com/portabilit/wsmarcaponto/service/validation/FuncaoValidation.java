package br.com.portabilit.wsmarcaponto.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Colaborador;
import br.com.portabilit.wsmarcaponto.domain.Funcao;
import br.com.portabilit.wsmarcaponto.domain.dto.FuncaoNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.FuncaoUpdateDTO;
import br.com.portabilit.wsmarcaponto.service.FuncaoService;
import br.com.portabilit.wsmarcaponto.service.SetorService;
import br.com.portabilit.wsmarcaponto.service.utils.TextBuilderService;
import br.com.portabilit.wsmarcaponto.service.validation.util.Validator;
import br.com.portabilit.wsmarcaponto.service.validation.util.ValidatorReturn;

@Service
public class FuncaoValidation {

	@Autowired
	private FuncaoService funcaoService;

	@Autowired
	private SetorValidation setorValidation;

	@Autowired
	private SetorService setorService;

	@Autowired
	private TextBuilderService textBuilderService;

	protected ValidatorReturn validateFuncaoById(Integer funcaoId) {
		Optional<Funcao> optFuncao = funcaoService.findByIdForValidation(funcaoId);
		if (optFuncao.isPresent()) {
			if (optFuncao.get().getAtivo()) {
				return new ValidatorReturn();
			} else {
				return new ValidatorReturn(textBuilderService.getValidationExceptionForInativeRegister("Função",
						"funcaoId", funcaoId.toString()));
			}
		} else {
			return new ValidatorReturn(textBuilderService.getValidationExceptionForNotFoundRegister("Função",
					"funcaoId", funcaoId.toString()));
		}
	}

	private ValidatorReturn validateFuncaoByNomeAndSetorId(String nome, Integer setorId, Integer id) {
		Optional<Funcao> optFuncao = funcaoService.findByNomeAndSetorId(nome, setorId);
		if (id == 0) {
			if (optFuncao.isPresent()) {
				return new ValidatorReturn(
						textBuilderService.getValidationExceptionForMultiRegister("Funcao", "nome", nome));
			} else {
				return new ValidatorReturn();
			}
		} else {
			if (optFuncao.isPresent() && !optFuncao.get().getId().equals(id)) {
				return new ValidatorReturn(
						textBuilderService.getValidationExceptionForMultiRegister("Funcao", "nome", nome));
			} else {
				return new ValidatorReturn();
			}
		}
	}

	private List<ValidatorReturn> validateFuncaoResponsavelByFuncaoResponsavelId(Integer funcaoResponsavelId) {
		List<ValidatorReturn> listValidatorReturn = new ArrayList<ValidatorReturn>();
		Optional<Funcao> optFuncao = funcaoService.findByIdForValidation(funcaoResponsavelId);
		if (optFuncao.isPresent()) {
			if (optFuncao.get().getResponsavel()) {
				listValidatorReturn.add(new ValidatorReturn());
			} else {
				listValidatorReturn.add(new ValidatorReturn(
						"A FUNÇÃO escolhida para ser FUNÇÃO RESPONSÁVEL é inválida! Para que a FUNÇÃO possa ser válida, a mesma deve ter seu cadastro de RESPONSÁVEL definido como VERDADEIRO."));
			}

			if (optFuncao.get().getAtivo()) {
				listValidatorReturn.add(new ValidatorReturn());
			} else {
				listValidatorReturn.add(
						new ValidatorReturn(textBuilderService.getValidationExceptionForInativeRegister("Função",
								"funcaoResponsavelId", funcaoResponsavelId.toString())));
			}
		} else {
			listValidatorReturn
					.add(new ValidatorReturn(textBuilderService.getValidationExceptionForNotFoundRegister("Função",
							"funcaoResponsavelId", funcaoResponsavelId.toString())));
		}
		return listValidatorReturn;
	}

	private ValidatorReturn validateSetorAtivoByFuncaoId(Integer funcaoId, Boolean ativo) {
		if (ativo == true) {
			Optional<Funcao> optFuncao = funcaoService.findByIdForValidation(funcaoId);
			return (setorService.findById(optFuncao.get().getSetorId()).getAtivo() ? new ValidatorReturn()
					: new ValidatorReturn(
							"Só é possível ATIVAR a função se o SETOR atrelado a ela também estiver ATIVO."));
		} else {
			return new ValidatorReturn();
		}
	}

	private ValidatorReturn validateIntegrityOnUpdate(Integer id, Boolean ativo) {
		if (ativo == false) {
			Optional<Funcao> optFuncao = funcaoService.findByIdForValidation(id);
			List<Colaborador> listColaborador = new ArrayList<Colaborador>();
			listColaborador = optFuncao.get().getColaboradores();
			if (listColaborador.size() > 0) {
				for (Colaborador colaborador : listColaborador) {
					if (colaborador.getAtivo().booleanValue() == true) {
						return new ValidatorReturn(
								textBuilderService.getIntegrityExceptionOnInactivate("Função", "Colaborador"));
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
		Optional<Funcao> optFuncao = funcaoService.findByIdForValidation(id);
		if (optFuncao.get().getColaboradores().size() > 0) {
			return new ValidatorReturn(textBuilderService.getIntegrityExceptionOnDelete("Função", "Colaborador"));
		} else {
			return new ValidatorReturn();
		}
	}

	public void validateNewFuncao(FuncaoNewDTO funcaoNewDTO) {
		Validator validator = new Validator();
		validator.validateOne(setorValidation.validateSetorById(funcaoNewDTO.getSetorId()));
		validator.validateOne(validateFuncaoByNomeAndSetorId(funcaoNewDTO.getNome(), funcaoNewDTO.getSetorId(), 0));
		if (funcaoNewDTO.getFuncaoResponsavelId() != 0) {
			validator.validateList(
					validateFuncaoResponsavelByFuncaoResponsavelId(funcaoNewDTO.getFuncaoResponsavelId()));
		}
		validator.validateResult(validator);
	}

	public void validateUpdateFuncao(FuncaoUpdateDTO funcaoUpdateDTO, Integer id) {
		Validator validator01 = new Validator();
		validator01.validateOne(validateIntegrityOnUpdate(id, funcaoUpdateDTO.getAtivo()));
		validator01.integrityResult(validator01);

		Validator validator02 = new Validator();
		validator02.validateOne(setorValidation.validateSetorById(funcaoUpdateDTO.getSetorId()));
		validator02.validateOne(validateSetorAtivoByFuncaoId(id, funcaoUpdateDTO.getAtivo()));
		validator02.validateOne(
				validateFuncaoByNomeAndSetorId(funcaoUpdateDTO.getNome(), funcaoUpdateDTO.getSetorId(), id));
		if (funcaoUpdateDTO.getFuncaoResponsavelId() != 0) {
			validator02.validateList(
					validateFuncaoResponsavelByFuncaoResponsavelId(funcaoUpdateDTO.getFuncaoResponsavelId()));
		}

		validator02.validateResult(validator02);
	}

	public void validateDeleteFuncao(Integer id) {
		Validator validator = new Validator();
		validator.validateOne(validateIntegrityOnDelete(id));

		validator.integrityResult(validator);
	}

}
