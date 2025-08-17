package com.developer.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.auth.entity.Funcionario;
import com.developer.auth.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public List<Funcionario> getAllFuncionarios() {
		return funcionarioRepository.findAll();
	}

}