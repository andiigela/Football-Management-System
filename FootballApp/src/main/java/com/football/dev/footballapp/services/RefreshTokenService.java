package com.football.dev.footballapp.services;

import com.football.dev.footballapp.models.RefreshToken;

import java.time.Instant;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username, String token, Instant expiryDate);
    RefreshToken findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
    String getRefreshTokenByUsername(String username);
}
