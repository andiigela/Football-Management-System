package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.JwtResponseDto;
import com.football.dev.footballapp.dto.LoginDto;
import com.football.dev.footballapp.dto.RefreshTokenRequestDto;
import com.football.dev.footballapp.dto.RegisterDto;
import com.football.dev.footballapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public JwtResponseDto login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/refreshToken")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        return authService.refreshToken(refreshTokenRequestDto);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto registerDto) {
        authService.register(registerDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return ResponseEntity.ok().body(response);
    }

}
