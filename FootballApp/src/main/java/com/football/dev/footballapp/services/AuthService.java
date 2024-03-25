package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.AuthResponseDTO;
import com.football.dev.footballapp.dto.LoginDto;
import com.football.dev.footballapp.dto.RegisterDto;

public interface AuthService {
    AuthResponseDTO login(LoginDto loginDto);
    void register(RegisterDto registerDto);
}
