package br.com.portabilit.wsmarcaponto.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.portabilit.wsmarcaponto.domain.enums.TipoDoRegistro;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Tipo do Registro", value = "Tipo do Registro")
@RestController
@RequestMapping(path = "/api/v1/tipo-do-registro")
public class TipoDoRegistroController {

	@ApiOperation(value = "Listar os Tipos De Registro disponíveis", notes = "End-Point para expor os códigos referentes a cada Tipo De Registro.")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<HashMap<Integer, String>>> findAll() {
		List<HashMap<Integer, String>> listTipoDoRegistro = new ArrayList<HashMap<Integer, String>>();
		for (Integer i = 0; i < TipoDoRegistro.values().length; i++) {
			TipoDoRegistro tipoDoRegistro = TipoDoRegistro.toEnum(i.intValue());

			HashMap<Integer, String> hashMapTipoDoRegistro = new HashMap<Integer, String>();
			hashMapTipoDoRegistro.put(i, tipoDoRegistro.toString() + " - " + tipoDoRegistro.getNome());
			listTipoDoRegistro.add(hashMapTipoDoRegistro);
		}
		return ResponseEntity.ok().body(listTipoDoRegistro);
	}
}