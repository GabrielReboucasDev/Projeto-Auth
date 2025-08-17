package com.developer.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.auth.dto.UsuarioDTO;
import com.developer.auth.entity.UsuarioEntity;
import com.developer.auth.repository.UsuarioRepository;
import com.developer.auth.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/criarUsuario")
	private ResponseEntity<?> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		
		UsuarioEntity usuarioExistente = usuarioRepository.findByLogin(usuarioDTO.login());
		
		if (usuarioExistente != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login não está disponíve!.");
	    }
		
		if (usuarioDTO.login() == null || usuarioDTO.login().isEmpty() || usuarioDTO.login().length() < 5) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login não pode ser menor do que 4 caracteres!.");
		}
		
		if (usuarioDTO.senha() == null || usuarioDTO.senha().isEmpty() || usuarioDTO.senha().length() < 4) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha não pode ser menor do que 4 caracteres!.");
		}
		
		if (usuarioDTO.nome() == null || usuarioDTO.nome().isEmpty() || usuarioDTO.nome().length() < 10) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome não pode ser nulo/vazio ou menor do que 10 caracteres!.");
		}
		
		if (usuarioDTO.role() == null || usuarioDTO.role().toString().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Favor informar o nível do usuário!.");
		}
		
		UsuarioDTO novoUsuario = usuarioService.salvar(usuarioDTO);
		
		if (novoUsuario != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao criar usuário!");
		}
	}

	@GetMapping("/admin")
	private String getAdmin() {
		return "ADMIN";
	}

	@GetMapping("/user")
	private String getuser() {
		return "USER";
	}

}
