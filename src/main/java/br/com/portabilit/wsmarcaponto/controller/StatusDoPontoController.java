package br.com.portabilit.wsmarcaponto.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.portabilit.wsmarcaponto.domain.enums.StatusDoPonto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Status do Ponto", value = "Status do Ponto")
@RestController
@RequestMapping(path = "/api/v1/status-do-ponto")
public class StatusDoPontoController {

	@ApiOperation(value = "Listar os Status do Ponto disponíveis", notes = "End-Point para expor os códigos referentes a cada Status de Ponto.")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<HashMap<Integer, String>>> findAll() {
		List<HashMap<Integer, String>> listStatusDoPonto = new ArrayList<HashMap<Integer, String>>();
		for (Integer i = 0; i < StatusDoPonto.values().length; i++) {
			StatusDoPonto tipoDoRegistro = StatusDoPonto.toEnum(i.intValue());

			HashMap<Integer, String> hashMapStatusDoPonto = new HashMap<Integer, String>();
			hashMapStatusDoPonto.put(i, tipoDoRegistro.toString() + " - " + tipoDoRegistro.getNome());
			listStatusDoPonto.add(hashMapStatusDoPonto);
		}
		return ResponseEntity.ok().body(listStatusDoPonto);
	}
}