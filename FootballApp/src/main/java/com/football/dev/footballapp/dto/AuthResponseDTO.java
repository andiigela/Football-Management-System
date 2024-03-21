package com.football.dev.footballapp.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken, String refreshToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
