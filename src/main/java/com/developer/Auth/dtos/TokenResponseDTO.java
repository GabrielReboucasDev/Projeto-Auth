package com.developer.Auth.dtos;

import lombok.Builder;

@Builder
public record TokenResponseDTO(String token, String refreshToken) {
}
