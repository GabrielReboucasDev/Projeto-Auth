package com.developer.auth.dto;

import com.developer.auth.enums.RoleEnum;

public record UsuarioDTO(String nome, String login, String senha, RoleEnum role) {
}
