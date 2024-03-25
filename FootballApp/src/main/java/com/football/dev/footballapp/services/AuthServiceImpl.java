package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.JwtResponseDto;
import com.football.dev.footballapp.dto.LoginDto;
import com.football.dev.footballapp.dto.RefreshTokenRequestDto;
import com.football.dev.footballapp.dto.RegisterDto;
import com.football.dev.footballapp.models.RefreshToken;
import com.football.dev.footballapp.models.Role;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.repository.RefreshTokenRepository;
import com.football.dev.footballapp.repository.RoleRepository;
import com.football.dev.footballapp.repository.UserRepository;
import com.football.dev.footballapp.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private JWTGenerator jwtGenerator;

    private AuthenticationManager authenticationManager;
    private RefreshTokenService refreshTokenService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           JWTGenerator jwtGenerator,AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public JwtResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(authentication.isAuthenticated()){
            JwtResponseDto tokenPair =this.jwtGenerator.generateTokens(loginDto.getUsername());
            return tokenPair;
        }
        throw new UsernameNotFoundException("Invalid user request");
    }

    @Override
    public void register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new IllegalArgumentException("Username is taken!");
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);
    }
    @Override
    public JwtResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshToken refreshToken = this.refreshTokenService.findByToken(refreshTokenRequestDto.getRefreshToken());
        UserEntity user = refreshToken.getUserInfo();
        JwtResponseDto jwtResponseDto = jwtGenerator.generateTokens(user.getUsername());
        return jwtResponseDto;
    }


}
