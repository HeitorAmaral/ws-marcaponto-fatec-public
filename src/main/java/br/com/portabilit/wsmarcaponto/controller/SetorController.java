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
import br.com.portabilit.wsmarcaponto.domain.Setor;
import br.com.portabilit.wsmarcaponto.domain.dto.SetorNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.SetorUpdateDTO;
import br.com.portabilit.wsmarcaponto.service.SetorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Setor", value = "Setor")
@RestController
@RequestMapping(path = "/api/v1/setor")
public class SetorController {

	@Autowired
	private SetorService setorService;

	@ApiOperation(value = "Listar todos os Setores")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Setor>> findAll() {
		return ResponseEntity.ok().body(setorService.findAll());
	}

	@ApiOperation(value = "Listar todos os Setores de acordo com o status")
	@RequestMapping(method = RequestMethod.GET, path = "/status")
	public ResponseEntity<List<Setor>> findByAtivo(@RequestParam Boolean ativo) {
		return ResponseEntity.ok().body(setorService.findByAtivo(ativo));
	}

	@ApiOperation(value = "Listar o Setor de acordo com o Id")
	@ApiResponses(value = {
			@ApiResponse(message = "Not Found - Não foi encontrado nenhum recurso cadastrado com os parâmetros informados.", code = 404, response = StandardError.class) })
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<Setor> findById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(setorService.findById(id));
	}

	@ApiOperation(value = "(insert) Inserir um novo Setor")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody SetorNewDTO setorNewDTO) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(setorService.insert(setorNewDTO).getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "(updateById) Atualizar um Setor de acordo com o Id")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Void> updateById(@Valid @RequestBody SetorUpdateDTO setorUpdateDTO,
			@PathVariable Integer id) {
		setorService.update(setorUpdateDTO, id);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "(deleteById) Deletar um Setor de acordo com o Id")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		setorService.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
