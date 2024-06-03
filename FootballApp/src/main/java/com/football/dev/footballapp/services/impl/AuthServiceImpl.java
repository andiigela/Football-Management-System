package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.*;
import com.football.dev.footballapp.models.*;
import com.football.dev.footballapp.models.ES.LeagueES;
import com.football.dev.footballapp.models.ES.UserEntityES;
import com.football.dev.footballapp.repository.esrepository.ClubRepositoryES;
import com.football.dev.footballapp.repository.esrepository.UserRepositoryES;
import com.football.dev.footballapp.repository.jparepository.ClubRepository;
import com.football.dev.footballapp.repository.jparepository.RoleRepository;
import com.football.dev.footballapp.repository.jparepository.UserRepository;
import com.football.dev.footballapp.security.JWTGenerator;
import com.football.dev.footballapp.services.AuthService;
import com.football.dev.footballapp.services.RefreshTokenService;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserRepositoryES userRepositoryES;
    private final RoleRepository roleRepository;
    private final ClubRepository clubRepository;
    private final ClubRepositoryES clubRepositoryES;
    private final JWTGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final  PasswordEncoder passwordEncoder;
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           ClubRepository clubRepository, JWTGenerator jwtGenerator,
                           AuthenticationManager authenticationManager,
                           RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder,
                           ClubRepositoryES clubRepositoryES, UserRepositoryES userRepositoryES) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.clubRepository = clubRepository;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.clubRepositoryES = clubRepositoryES;
        this.userRepositoryES = userRepositoryES;
    }

    @Override
    public JwtResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            String userEmail = loginDto.getEmail();

            UserEntity user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
            Role role = user.getRole();
            boolean enabled = user.isEnabled();
            return jwtGenerator.generateTokens(user.getId(), userEmail, role.getDescription(), enabled);
        }
        throw new UsernameNotFoundException("Invalid user request");
    }

    @Override
    public void register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new IllegalArgumentException("Username is taken!");
        }

        UserEntity user = new UserEntity();
        user.setEmail(registerDto.getEmail());

        String hashedPassword = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(hashedPassword);

        Role role = roleRepository.findByDescription("ADMIN_CLUB")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));
        user.setRole(role);
        UserEntity savedUser = userRepository.save(user);
        String esId = UUID.randomUUID().toString();
        UserEntityES userES = new UserEntityES();
        userES.setId(esId);
        userES.setDbId(savedUser.getId());
        userES.setEmail(savedUser.getEmail());
        userES.setEnabled(savedUser.isEnabled());
        userES.setDeleted(false);
        userRepositoryES.save(userES);
        Club club = new Club();
        club.setName(registerDto.getClubName());
        club.setUser(user);
        clubRepository.save(club);
        clubRepositoryES.save(club);
    }

    @Override
    public JwtResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshToken refreshToken = this.refreshTokenService.findByToken(refreshTokenRequestDto.getRefreshToken());


        if (refreshToken == null || refreshToken.isExpired()) {
            return new JwtResponseDto(null, null);
        }

        UserEntity user = refreshToken.getUserInfo();
        String newAccessToken = jwtGenerator.generateAccessToken(user.getEmail(), user.getId(), user.getRole().getDescription(), user.isEnabled());
        return new JwtResponseDto(newAccessToken, null);
    }
}
