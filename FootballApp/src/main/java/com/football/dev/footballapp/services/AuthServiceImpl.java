package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.AuthResponseDTO;
import com.football.dev.footballapp.dto.LoginDto;
import com.football.dev.footballapp.dto.RegisterDto;
import com.football.dev.footballapp.models.Role;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.repository.RoleRepository;
import com.football.dev.footballapp.repository.UserRepository;
import com.football.dev.footballapp.security.JWTGenerator;
import com.football.dev.footballapp.security.TokenPair;
import com.football.dev.footballapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public AuthResponseDTO login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate tokens
        TokenPair tokenPair = jwtGenerator.generateTokens(authentication);

        // Return tokens in response
        return new AuthResponseDTO(tokenPair.getAccessToken(), tokenPair.getRefreshToken());
    }

    @Override
    public void register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("Username is taken!");
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);
    }
}
