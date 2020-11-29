package br.com.portabilit.wsmarcaponto.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.portabilit.wsmarcaponto.domain.enums.DiaDaSemana;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Dia da Semana", value = "Dia da Semana")
@RestController
@RequestMapping(path = "/api/v1/dia-da-semana")
public class DiaDaSemanaController {

	@ApiOperation(value = "Listar os Dias da Semana disponíveis", notes = "End-Point para expor os códigos referentes a cada Dia Da Semana.")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<HashMap<Integer, String>>> findAll() {
		List<HashMap<Integer, String>> listDiaDaSemana = new ArrayList<HashMap<Integer, String>>();
		for (Integer i = 0; i < DiaDaSemana.values().length; i++) {
			DiaDaSemana diaDaSemana = DiaDaSemana.toEnum(i.intValue());

			HashMap<Integer, String> hashMapDiaDaSemana = new HashMap<Integer, String>();
			hashMapDiaDaSemana.put(i, diaDaSemana.toString() + " - " + diaDaSemana.getNome());
			listDiaDaSemana.add(hashMapDiaDaSemana);
		}
		return ResponseEntity.ok().body(listDiaDaSemana);
	}
}