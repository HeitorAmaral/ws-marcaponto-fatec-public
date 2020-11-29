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
import br.com.portabilit.wsmarcaponto.domain.Colaborador;
import br.com.portabilit.wsmarcaponto.domain.dto.ColaboradorNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.ColaboradorUpdateDTO;
import br.com.portabilit.wsmarcaponto.service.ColaboradorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Colaborador", value = "Colaborador")
@RestController
@RequestMapping(path = "/api/v1/colaborador")
public class ColaboradorController {

	@Autowired
	private ColaboradorService colaboradorService;

	@ApiOperation(value = "Listar todos os Colaboradores")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Colaborador>> findAll() {
		return ResponseEntity.ok().body(colaboradorService.findAll());
	}

	@ApiOperation(value = "Listar todos os Colaboradores de acordo com a Situação")
	@RequestMapping(method = RequestMethod.GET, path = "/status")
	public ResponseEntity<List<Colaborador>> findByAtivo(@RequestParam Boolean ativo) {
		return ResponseEntity.ok().body(colaboradorService.findByAtivo(ativo));
	}

	@ApiOperation(value = "Listar todos os Colaboradores de acordo com o Gestor responsável")
	@RequestMapping(method = RequestMethod.GET, path = "/find-by-gestor/{gestorId}")
	public ResponseEntity<List<Colaborador>> findByGestorId(@PathVariable Integer gestorId) {
		return ResponseEntity.ok().body(colaboradorService.findByGestorId(gestorId));
	}

	@ApiOperation(value = "Listar o Colaborador de acordo com o Id")
	@ApiResponses(value = {
			@ApiResponse(message = "Not Found - Não foi encontrado nenhum recurso cadastrado com os parâmetros informados.", code = 404, response = StandardError.class) })
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<Colaborador> findById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(colaboradorService.findById(id));
	}

	@ApiOperation(value = "Inserir um novo Colaborador")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ColaboradorNewDTO colaboradorNewDTO) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(colaboradorService.insert(colaboradorNewDTO).getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Atualizar um Colaborador de acordo com o Id")
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ColaboradorUpdateDTO colaboradorAlterDTO,
			@PathVariable Integer id) {
		colaboradorService.update(colaboradorAlterDTO, id);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Deletar um Colaborador de acordo com o Id")
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		colaboradorService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
