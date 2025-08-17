package com.developer.auth.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.developer.auth.entity.UsuarioEntity;
import com.developer.auth.repository.UsuarioRepository;
import com.developer.auth.service.AutenticacaoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AutenticacaoService autenticacaoService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = extraiTokenHeader(request);

		if (token != null) {
			String login = autenticacaoService.validaTokenJwt(token);
			UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(login);
			var authentication = new UsernamePasswordAuthenticationToken(usuarioEntity, null, usuarioEntity.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}

	public String extraiTokenHeader(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		
		return null;
	}

}
