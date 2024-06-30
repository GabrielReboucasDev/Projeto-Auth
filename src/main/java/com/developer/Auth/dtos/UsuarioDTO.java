package com.developer.Auth.dtos;

import com.developer.Auth.enums.RoleEnum;

public record UsuarioDTO(String nome, String login, String senha, RoleEnum role) {
}
