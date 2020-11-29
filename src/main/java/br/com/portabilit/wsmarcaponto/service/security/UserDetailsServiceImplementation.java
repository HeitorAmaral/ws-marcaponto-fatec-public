package br.com.portabilit.wsmarcaponto.service.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.security.Usuario;
import br.com.portabilit.wsmarcaponto.repository.security.UsuarioRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username);
		if (usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		usuario.setAuthorities(usuario.getPerfis().stream().map(x -> new SimpleGrantedAuthority(x.getNome()))
				.collect(Collectors.toList()));
		return usuario;
	}
}
