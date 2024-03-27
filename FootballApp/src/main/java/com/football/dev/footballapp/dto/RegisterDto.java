package com.football.dev.footballapp.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String clubName;
    private String email;
}
