package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.JwtResponseDto;
import com.football.dev.footballapp.dto.LoginDto;
import com.football.dev.footballapp.dto.RefreshTokenRequestDto;
import com.football.dev.footballapp.dto.RegisterDto;

public interface AuthService {
    JwtResponseDto login(LoginDto loginDto);
    void register(RegisterDto registerDto);
    JwtResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);
}
