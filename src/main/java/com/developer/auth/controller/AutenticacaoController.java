package com.developer.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.developer.auth.dto.AuthDTO;
import com.developer.auth.dto.RequestRefreshDTO;
import com.developer.auth.dto.TokenResponseDTO;
import com.developer.auth.service.AutenticacaoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AutenticacaoService autenticacaoService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthDTO authDTO) {

		try {
			var usuarioAuthenticationToken = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.senha());
			authenticationManager.authenticate(usuarioAuthenticationToken);
			TokenResponseDTO tokenResponse = autenticacaoService.obterToken(authDTO);
			System.out.println("USUÁRIO AUTENTICADO: " + usuarioAuthenticationToken.toString());
			return ResponseEntity.ok(tokenResponse);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário inexistente ou senha incorreta!");
		}

	}
	
	@PostMapping("/logout")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		SecurityContextHolder.clearContext();
		return ResponseEntity.ok("Logout realizado com sucesso!");
	}

	@PostMapping("/refresh-token")
	@ResponseStatus(HttpStatus.OK)
	public TokenResponseDTO authRefreshToken(@RequestBody RequestRefreshDTO requestRefreshDTO) {
		return autenticacaoService.obterRefreshToken(requestRefreshDTO.refreshToken());
	}

}
