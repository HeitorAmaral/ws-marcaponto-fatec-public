package br.com.portabilit.wsmarcaponto.service.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.text.MaskFormatter;

import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.enums.DiaDaSemana;
import br.com.portabilit.wsmarcaponto.service.exception.GenericException;

@Service
public class ConverterService {

	public Date stringToDate(String date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateParsed = null;
		try {
			dateParsed = simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateParsed;
	}

	public DiaDaSemana getWeekDayByDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		switch (day) {
		case 1:
			return DiaDaSemana.DOM;
		case 2:
			return DiaDaSemana.SEG;
		case 3:
			return DiaDaSemana.TER;
		case 4:
			return DiaDaSemana.QUA;
		case 5:
			return DiaDaSemana.QUI;
		case 6:
			return DiaDaSemana.SEX;
		case 7:
			return DiaDaSemana.SAB;
		default:
			return null;
		}
	}

	public String removeMask(String text) {
		return text.replaceAll("[^0-9]", "");
	}

	public String addMaskCpf(String cpf) {
		try {
			MaskFormatter maskFormatter = new MaskFormatter("###.###.###-##");
			maskFormatter.setValueContainsLiteralCharacters(false);
			return maskFormatter.valueToString(cpf);
		} catch (ParseException e) {
			throw new GenericException(
					"Erro no método addMaskCpf() do tipo " + this.getClass().getSimpleName().replaceAll("Service", "")
							+ ". " + e.getCause().getCause().getMessage());
		}
	}

	public String addMaskCnpj(String cnpj) {
		try {
			MaskFormatter maskFormatter = new MaskFormatter("##.###.###/####-##");
			maskFormatter.setValueContainsLiteralCharacters(false);
			return maskFormatter.valueToString(cnpj);
		} catch (ParseException e) {
			throw new GenericException(
					"Erro no método addMaskCnpj() do tipo " + this.getClass().getSimpleName().replaceAll("Service", "")
							+ ". " + e.getCause().getCause().getMessage());
		}
	}

	public Integer hourToMinute(String horaParam) {
		int horaOriginal = Integer.parseInt(horaParam.split(":")[0]);
		int minutoOriginal = Integer.parseInt(horaParam.split(":")[1]);
		return ((horaOriginal * 60) + minutoOriginal);
	}

	public String minuteToHour(Integer minutosParam) {
		Integer minutos = (minutosParam % 60);
		Integer horas = (minutosParam - minutos) / 60;

		String minutosString = minutos.toString();
		String horasString = horas.toString();

		return ((horasString.length() == 1 ? "0" + horasString : horasString) + ":"
				+ (minutosString.length() == 1 ? "0" + minutosString : minutosString));
	}
}
