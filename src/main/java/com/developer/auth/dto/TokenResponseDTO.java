package com.developer.auth.dto;

import lombok.Builder;

@Builder
public record TokenResponseDTO(String token, String refreshToken) {
}
