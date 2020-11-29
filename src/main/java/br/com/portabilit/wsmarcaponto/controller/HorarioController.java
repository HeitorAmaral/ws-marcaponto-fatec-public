package br.com.portabilit.wsmarcaponto.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.portabilit.wsmarcaponto.controller.exception.StandardError;
import br.com.portabilit.wsmarcaponto.domain.dto.HorarioAlterDTO;
import br.com.portabilit.wsmarcaponto.domain.dto.HorarioDTO;
import br.com.portabilit.wsmarcaponto.service.HorarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Horario", value = "Horario")
@RestController
@RequestMapping(path = "/api/v1/horario")
public class HorarioController {

	@Autowired
	private HorarioService horarioService;

	@ApiOperation(value = "Listar todos os Horarios")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<HorarioDTO>> findAll() {
		return ResponseEntity.ok().body(
				horarioService.findAll().stream().map(horario -> new HorarioDTO(horario)).collect(Collectors.toList()));
	}

	@ApiOperation(value = "Listar o Horario de acordo com o Id")
	@ApiResponses(value = {
			@ApiResponse(message = "Not Found - Não foi encontrado nenhum recurso cadastrado com os parâmetros informados.", code = 404, response = StandardError.class) })
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<HorarioDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(new HorarioDTO(horarioService.findById(id)));
	}

	@ApiOperation(value = "(insert) Inserir um novo Horario")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody HorarioAlterDTO horarioAlterDTO) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(horarioService.insert(horarioAlterDTO).getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "(updateById) Atualizar um Horario de acordo com o Id")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Void> updateById(@Valid @RequestBody HorarioAlterDTO horarioAlterDTO,
			@PathVariable Integer id) {
		horarioService.update(horarioAlterDTO, id);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "(deleteById) Deletar um Horario de acordo com o Id")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		horarioService.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
