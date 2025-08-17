package com.developer.auth.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.developer.auth.dto.AuthDTO;
import com.developer.auth.dto.TokenResponseDTO;
import com.developer.auth.entity.UsuarioEntity;
import com.developer.auth.repository.UsuarioRepository;
import com.developer.auth.service.AutenticacaoService;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {
	
	@Value("${auth.jwt.token.secret}")
	private String secretKey;
	
	@Value("${auth.jwt.token.expiration}")
	private Integer horaExpiracaoToken;

	@Value("${auth.jwt.refresh-token.expiration}")
	private Integer horaExpiracaoRefreshToken;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		 UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(login);
        if (usuarioEntity == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + login);
        }
        return usuarioEntity;
	}

	@Override
	public TokenResponseDTO obterToken(AuthDTO authDTO) {
		UsuarioEntity usuarioLogin = usuarioRepository.findByLogin(authDTO.login());
	    if (usuarioLogin == null) {
	        throw new UsernameNotFoundException("Usuário não encontrado: " + authDTO.login());
	    }
	    System.out.println("OBTER TOKEN USUÁRIO: " + usuarioLogin.toString());
		return TokenResponseDTO
				.builder()
				.token(gerarTokenJwt(usuarioLogin, horaExpiracaoToken))
				.refreshToken(gerarTokenJwt(usuarioLogin, horaExpiracaoRefreshToken))
				.build();
	}

	public String gerarTokenJwt(UsuarioEntity usuarioEntity, Integer expiration) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			
			return JWT.create()
					.withIssuer("Auth")
					.withSubject(usuarioEntity.getLogin())
					.withExpiresAt(geraFataExpiracao(expiration))
					.sign(algorithm);
		} catch (JWTCreationException ex) {
			throw new RuntimeException("Erro ao tentar gerar o token! " + ex.getMessage());
		}
	}
	
	public String validaTokenJwt(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			
			return JWT.require(algorithm)
					.withIssuer("Auth")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException ex) {
			throw new RuntimeException("Erro ao validar token!" + ex.getMessage());
		}
	}
	
	@Override
	public TokenResponseDTO obterRefreshToken(String refreshToken) {
		
		String login = validaTokenJwt(refreshToken);
		UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(login);
		
		if (login == null) {
			throw new RuntimeException("Falhou ao gerar refresh token!");
		}
		
		try {
			var authentication = new UsernamePasswordAuthenticationToken(usuarioEntity, null, usuarioEntity.getAuthorities());
	
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			return TokenResponseDTO
					.builder()
					.token(gerarTokenJwt(usuarioEntity, horaExpiracaoToken))
					.refreshToken(gerarTokenJwt(usuarioEntity, horaExpiracaoRefreshToken))
					.build();
		} catch (JWTVerificationException ex) {
			throw new RuntimeException("Erro ao obter refresh token!" + ex.getMessage());
		}
	}

	private Instant geraFataExpiracao(Integer expiration) {
		return LocalDateTime.now()
				.plusHours(expiration)
				.toInstant(ZoneOffset.of("-03:00"));
	}

}
