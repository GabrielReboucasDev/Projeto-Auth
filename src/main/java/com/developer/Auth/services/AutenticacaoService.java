package com.developer.Auth.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.developer.Auth.dtos.AuthDTO;
import com.developer.Auth.dtos.TokenResponseDTO;

public interface AutenticacaoService extends UserDetailsService{
	
	public TokenResponseDTO obterToken(AuthDTO authDTO);
	
	public String validaTokenJwt(String token);

	public TokenResponseDTO obterRefreshToken(String refreshToken);

}
