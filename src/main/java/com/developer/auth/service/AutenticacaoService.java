package com.developer.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.developer.auth.dto.AuthDTO;
import com.developer.auth.dto.TokenResponseDTO;

public interface AutenticacaoService extends UserDetailsService{
	
	public TokenResponseDTO obterToken(AuthDTO authDTO);
	
	public String validaTokenJwt(String token);

	public TokenResponseDTO obterRefreshToken(String refreshToken);

}
