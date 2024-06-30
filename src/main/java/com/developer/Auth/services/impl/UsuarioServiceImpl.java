package com.developer.Auth.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.developer.Auth.dtos.UsuarioDTO;
import com.developer.Auth.entity.UsuarioEntity;
import com.developer.Auth.repositories.UsuarioRepository;
import com.developer.Auth.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
		var passwordHash = passwordEncoder.encode(usuarioDTO.senha());		
		UsuarioEntity usuarioEntity = new UsuarioEntity(usuarioDTO.nome(), usuarioDTO.login(), passwordHash, usuarioDTO.role());
		UsuarioEntity novoUsuario = usuarioRepository.save(usuarioEntity);
		System.out.println("USUÁRIO CRIADO: " + novoUsuario.toString());
		return new UsuarioDTO(novoUsuario.getNome(), novoUsuario.getLogin(), novoUsuario.getSenha(), novoUsuario.getRole());
	}

}
