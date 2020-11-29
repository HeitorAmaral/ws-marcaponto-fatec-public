package br.com.portabilit.wsmarcaponto.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.portabilit.wsmarcaponto.controller.exception.StandardError;
import br.com.portabilit.wsmarcaponto.domain.dto.PontoDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.PontoNewDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.PontoUpdateDTO;
import br.com.portabilit.wsmarcaponto.service.PontoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Ponto", value = "Ponto")
@RestController
@RequestMapping(path = "/api/v1/ponto")
public class PontoController {

	@Autowired
	private PontoService pontoService;

	@ApiOperation(value = "Listar todos os Pontos")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PontoDTO>> findAll() {
		return ResponseEntity.ok()
				.body(pontoService.findAll().stream().map(ponto -> new PontoDTO(ponto)).collect(Collectors.toList()));
	}

	@ApiOperation(value = "Listar o Ponto de acordo com o Id")
	@ApiResponses(value = {
			@ApiResponse(message = "Not Found - Não foi encontrado nenhum recurso cadastrado com os parâmetros informados.", code = 404, response = StandardError.class) })
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<PontoDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(new PontoDTO(pontoService.findById(id)));
	}

	@ApiOperation(value = "Listar Pontos de acordo com o Id do Colaborador e o Status")
	@RequestMapping(method = RequestMethod.GET, path = "/colaborador/{colaboradorId}")
	public ResponseEntity<List<PontoDTO>> findByColaboradorIdAndStatus(@PathVariable Integer colaboradorId,
			@RequestParam(required = false) Integer statusDoPonto) {
		if (statusDoPonto == null) {
			return ResponseEntity.ok().body(pontoService.findByColaboradorId(colaboradorId).stream()
					.map(ponto -> new PontoDTO(ponto)).collect(Collectors.toList()));
		} else {
			return ResponseEntity.ok().body(pontoService.findByColaboradorIdAndStatus(colaboradorId, statusDoPonto)
					.stream().map(ponto -> new PontoDTO(ponto)).collect(Collectors.toList()));
		}
	}

	@ApiOperation(value = "Listar Pontos a aprovar de acordo com o Id do Gestor")
	@RequestMapping(method = RequestMethod.GET, path = "/to-approve-by-gestor/{colaboradorId}")
	public ResponseEntity<List<PontoDTO>> findToApproveByColaboradorId(@PathVariable Integer colaboradorId) {
		return ResponseEntity.ok().body(pontoService.findToApproveByColaboradorId(colaboradorId).stream()
				.map(ponto -> new PontoDTO(ponto)).collect(Collectors.toList()));
	}

	@CrossOrigin
	@ApiOperation(value = "(insert) Inserir um novo Ponto")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PontoNewDTO pontoNewDTO) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(pontoService.marcarPonto(pontoNewDTO).getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "(updateById) Atualizar um Ponto de acordo com o Id")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Void> updateById(@Valid @RequestBody PontoUpdateDTO pontoUpdateDTO,
			@PathVariable Integer id) {
		pontoService.updateById(id, pontoUpdateDTO);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "approve/{id}")
	public ResponseEntity<Void> approveById(@PathVariable Integer id) {
		pontoService.approveById(id);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "disapprove/{id}")
	public ResponseEntity<Void> disapproveById(@PathVariable Integer id, @Valid @RequestBody String obs) {
		pontoService.disapproveById(id, obs);
		return ResponseEntity.ok().build();
	}
}
