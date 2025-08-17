package com.developer.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.developer.auth.dto.UsuarioDTO;
import com.developer.auth.entity.UsuarioEntity;
import com.developer.auth.repository.UsuarioRepository;
import com.developer.auth.service.UsuarioService;

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
