package com.football.dev.footballapp.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String password;
    private String clubName;

}
