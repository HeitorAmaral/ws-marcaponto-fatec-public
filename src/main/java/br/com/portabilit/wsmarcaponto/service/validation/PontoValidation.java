package br.com.portabilit.wsmarcaponto.service.validation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Horario;
import br.com.portabilit.wsmarcaponto.domain.Ponto;
import br.com.portabilit.wsmarcaponto.domain.dto.PontoNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.PontoUpdateDTO;
import br.com.portabilit.wsmarcaponto.domain.enums.TipoDoRegistro;
import br.com.portabilit.wsmarcaponto.service.PontoService;
import br.com.portabilit.wsmarcaponto.service.utils.ConverterService;
import br.com.portabilit.wsmarcaponto.service.validation.util.Validator;
import br.com.portabilit.wsmarcaponto.service.validation.util.ValidatorReturn;

@Service
public class PontoValidation {

	@Autowired
	private PontoService pontoService;

	@Autowired
	private ColaboradorValidation colaboradorValidation;

	@Autowired
	private TipoDoRegistroValidation tipoDoRegistroValidation;

	@Autowired
	private ConverterService converterService;

	private ValidatorReturn validateData(Date date) {
		if (date.after(new Date())) {
			return new ValidatorReturn("A data informada é maior que a data atual.");
		} else {
			return new ValidatorReturn();
		}
	}

	private ValidatorReturn validatePontoByDiaAndColaboradorId(PontoNewDTO pontoNewDTO) {

		Date dataParsed = converterService.stringToDate(pontoNewDTO.getData());
		List<Ponto> listPontoByDia = new ArrayList<Ponto>();
		listPontoByDia = pontoService.findByDataAndColaboradorId(dataParsed, pontoNewDTO.getColaboradorId());
		if (listPontoByDia.size() > 4) {
			return new ValidatorReturn(
					"Na data atual ja foram marcados todos os 4 pontos permitidos por dia! Verifique para corrigir os pontos caso tenham ocorrido erros.");
		} else {
			return new ValidatorReturn();
		}
	}

	public void validateHorario(List<Horario> listHorario, TipoDoRegistro tipoDoRegistro) {
		Validator validator = new Validator();
		if (listHorario.size() > 0) {
			Boolean error = true;
			for (Horario horario : listHorario) {
				if (horario.getTipoDoRegistro().equals(tipoDoRegistro)) {
					error = false;
				}
			}
			if (error) {
				validator.validateOne(new ValidatorReturn(
						"Não foi encontrado nenhum HORÁRIO atrelado ao COLABORADOR com o TIPO DO REGISTRO informado. Favor verificar a parametrização."));
			}
		} else {
			validator.validateOne(new ValidatorReturn(
					"Não foi encontrado nenhum HORÁRIO atrelado ao EXPEDIENTE e DIA DA SEMANA informado. Favor verificar a parametrização."));
		}

		validator.validateResult(validator);
	}

	public ValidatorReturn validatePontoByDia(Integer id, Date dataParsed, Integer colaboradorId) {
		List<Ponto> listPontoByDia = new ArrayList<Ponto>();
		listPontoByDia = pontoService.findByDataAndColaboradorId(dataParsed, colaboradorId);
		if (listPontoByDia.size() >= 4) {
			for (Ponto ponto : listPontoByDia) {
				if (ponto.getId().equals(id)) {
					return new ValidatorReturn();
				} else {
					return new ValidatorReturn(
							"Na data atual ja foram marcados todos os 4 pontos permitidos por dia! O Ponto que está sendo alterado não deve ser alocado no dia "
									+ dataParsed + ".");
				}
			}
		}
		return new ValidatorReturn();
	}

	public void validateNewPonto(PontoNewDTO pontoNewDTO) {
		Validator validator = new Validator();
		validator.validateOne(colaboradorValidation.validateColaboradorById(pontoNewDTO.getColaboradorId()));
		validator.validateOne(validateData(converterService.stringToDate(pontoNewDTO.getData())));
		if (validator.getListFalse().size() == 0) {
			validator.validateOne(validatePontoByDiaAndColaboradorId(pontoNewDTO));
		}
		validator.validateResult(validator);
	}

	public void validateUpdatePonto(Integer id, PontoUpdateDTO pontoUpdateDTO, Integer colaboradorId) {
		Validator validator = new Validator();
		validator.validateOne(validateData(converterService.stringToDate(pontoUpdateDTO.getData())));
		validator
				.validateOne(tipoDoRegistroValidation.validateTipoDoRegistroById(pontoUpdateDTO.getTipoDoRegistroId()));
		validator.validateOne(
				validatePontoByDia(id, converterService.stringToDate(pontoUpdateDTO.getData()), colaboradorId));
		validator.validateResult(validator);
	}
}
