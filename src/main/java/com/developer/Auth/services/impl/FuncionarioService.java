package com.developer.Auth.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.Auth.entity.Funcionario;
import com.developer.Auth.repositories.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public List<Funcionario> getAllFuncionarios() {
		return funcionarioRepository.findAll();
	}

}