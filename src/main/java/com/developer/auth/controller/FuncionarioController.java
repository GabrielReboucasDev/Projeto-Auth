package com.developer.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.developer.auth.entity.Funcionario;
import com.developer.auth.service.impl.FuncionarioService;

@RestController
@RequestMapping("/funcionarios/lista")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Funcionario>> getAllFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.getAllFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }
    
}