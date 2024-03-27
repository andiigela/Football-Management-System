package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.JwtResponseDto;
import com.football.dev.footballapp.dto.LoginDto;
import com.football.dev.footballapp.dto.RefreshTokenRequestDto;
import com.football.dev.footballapp.dto.RegisterDto;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.RefreshToken;
import com.football.dev.footballapp.models.Role;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.repository.ClubRepository;
import com.football.dev.footballapp.repository.RoleRepository;
import com.football.dev.footballapp.repository.UserRepository;
import com.football.dev.footballapp.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ClubRepository clubRepository;
    private JWTGenerator jwtGenerator;
    private AuthenticationManager authenticationManager;
    private RefreshTokenService refreshTokenService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           ClubRepository clubRepository,
                           JWTGenerator jwtGenerator, AuthenticationManager authenticationManager,
                           RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.clubRepository = clubRepository;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
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

        String hashedPassword = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(hashedPassword);

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);


        Club club = new Club();
        club.setName(registerDto.getClubName());
        club.setUser(user);
        clubRepository.save(club);
    }

    @Override
    public JwtResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshToken refreshToken = this.refreshTokenService.findByToken(refreshTokenRequestDto.getRefreshToken());

        // Check if refresh token exists and is not expired
        if (refreshToken == null || refreshToken.isExpired()) {
            return new JwtResponseDto(null, null);
        }

        UserEntity user = refreshToken.getUserInfo();
        String newAccessToken = jwtGenerator.generateAccessToken(user.getUsername());
        return new JwtResponseDto(newAccessToken, null);
    }
}
