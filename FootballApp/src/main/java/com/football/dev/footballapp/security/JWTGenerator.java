package com.football.dev.footballapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTGenerator {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public TokenPair generateTokens(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();

        // Generate access token
        Date accessTokenExpiry = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION * 1000);
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(accessTokenExpiry)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // Generate refresh token
        Date refreshTokenExpiry = new Date(currentDate.getTime() + SecurityConstants.REFRESH_TOKEN_EXPIRATION * 1000);
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(refreshTokenExpiry)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return new TokenPair(accessToken, refreshToken);
    }
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
        }
    }
}
