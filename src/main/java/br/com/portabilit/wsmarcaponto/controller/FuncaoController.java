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
import br.com.portabilit.wsmarcaponto.domain.Funcao;
import br.com.portabilit.wsmarcaponto.domain.dto.FuncaoNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.FuncaoUpdateDTO;
import br.com.portabilit.wsmarcaponto.service.FuncaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Função", value = "Função")
@RestController
@RequestMapping(path = "/api/v1/funcao")
public class FuncaoController {

	@Autowired
	private FuncaoService funcaoService;

	@ApiOperation(value = "Listar todas as Funções")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Funcao>> findAll() {
		return ResponseEntity.ok().body(funcaoService.findAll());
	}

	@ApiOperation(value = "Listar a Função de acordo com o Id")
	@ApiResponses(value = {
			@ApiResponse(message = "Not Found - Não foi encontrado nenhum recurso cadastrado com os parâmetros informados.", code = 404, response = StandardError.class) })
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<Funcao> findById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(funcaoService.findById(id));
	}

	@ApiOperation(value = "Listar todas as Funções de acordo com o status")
	@RequestMapping(method = RequestMethod.GET, path = "/status")
	public ResponseEntity<List<Funcao>> findByAtivo(@RequestParam Boolean ativo) {
		return ResponseEntity.ok().body(funcaoService.findByAtivo(ativo));
	}

	@ApiOperation(value = "Listar as Funções atreladas de acordo com o Setor Id")
	@RequestMapping(method = RequestMethod.GET, path = "/setor/{id}")
	public ResponseEntity<List<Funcao>> findBySetorId(@PathVariable Integer setorId) {
		return ResponseEntity.ok().body(funcaoService.findBySetorId(setorId));
	}

	@ApiOperation(value = "Listar todas as Funções de acordo com Setor")
	@RequestMapping(method = RequestMethod.GET, path = "/setor-ativo")
	public ResponseEntity<List<Funcao>> findBySetorIdAndAtivo(@RequestParam Integer setorId,
			@RequestParam Boolean ativo) {
		return ResponseEntity.ok().body(funcaoService.findBySetorIdAndAtivo(setorId, ativo));
	}

	@ApiOperation(value = "Inserir uma nova Funcao")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody FuncaoNewDTO funcaoNewDTO) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(funcaoService.insert(funcaoNewDTO).getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Atualizar uma Função de acordo com o Id")
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody FuncaoUpdateDTO funcaoAlterDTO, @PathVariable Integer id) {
		funcaoService.update(funcaoAlterDTO, id);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Deletar uma Função de acordo com o Id")
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		funcaoService.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
