package br.com.portabilit.wsmarcaponto.controller.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.portabilit.wsmarcaponto.domain.security.Usuario;
import br.com.portabilit.wsmarcaponto.security.JWTUtil;
import br.com.portabilit.wsmarcaponto.service.security.UsuarioService;
import io.swagger.annotations.Api;

@Api(tags = "Segurança", value = "Segurança")
@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		Usuario user = UsuarioService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/usuario")
	public ResponseEntity<Usuario> getUserAuthenticated() {
		return ResponseEntity.ok(UsuarioService.authenticated());
	}

	@RequestMapping(method = RequestMethod.PATCH, path = "/password")
	public ResponseEntity<Usuario> changePassword(@RequestBody String password) {
		return ResponseEntity.ok(usuarioService.changePassword(password));
	}
}
