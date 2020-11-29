package br.com.portabilit.wsmarcaponto.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.portabilit.wsmarcaponto.controller.exception.StandardError;
import br.com.portabilit.wsmarcaponto.domain.Expediente;
import br.com.portabilit.wsmarcaponto.domain.dto.ExpedienteNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.ExpedienteUpdateDTO;
import br.com.portabilit.wsmarcaponto.service.ExpedienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Expediente", value = "Expediente")
@RestController
@RequestMapping(path = "/api/v1/expediente")
public class ExpedienteController {

	@Autowired
	private ExpedienteService expedienteService;

	@ApiOperation(value = "Listar todos os Expedientes")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Expediente>> findAll() {
		return ResponseEntity.ok().body(expedienteService.findAll());
	}

	@ApiOperation(value = "Listar todos os Expedientes de acordo com o status")
	@RequestMapping(method = RequestMethod.GET, path = "/status")
	public ResponseEntity<List<Expediente>> findByAtivo(@RequestParam Boolean ativo) {
		return ResponseEntity.ok().body(expedienteService.findByAtivo(ativo));
	}

	@ApiOperation(value = "Listar o Expediente de acordo com o Id")
	@ApiResponses(value = {
			@ApiResponse(message = "Not Found - Não foi encontrado nenhum recurso cadastrado com os parâmetros informados.", code = 404, response = StandardError.class) })
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<Expediente> findById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(expedienteService.findById(id));
	}

	@ApiOperation(value = "(insert) Inserir um novo Expediente")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ExpedienteNewDTO expedienteNewDTO) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(expedienteService.insert(expedienteNewDTO).getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "(updateById) Atualizar um Expediente de acordo com o Id")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Void> updateById(@Valid @RequestBody ExpedienteUpdateDTO expedienteUpdateDTO,
			@PathVariable Integer id) {
		expedienteService.update(expedienteUpdateDTO, id);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "(deleteById) Deletar um Expediente de acordo com o Id")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		expedienteService.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
