    package com.football.dev.footballapp.security;

    import com.football.dev.footballapp.dto.JwtResponseDto;
    import com.football.dev.footballapp.services.RefreshTokenService;
    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
    import org.springframework.stereotype.Component;

    import java.security.Key;
    import java.util.Date;

    @Component
    public class JWTGenerator {
        private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        private final RefreshTokenService refreshTokenService;
        public JWTGenerator(RefreshTokenService refreshTokenService){
            this.refreshTokenService=refreshTokenService;
        }

        public JwtResponseDto generateTokens(Long userId, String username, String role, boolean enabled) {
            Date currentDate = new Date();

            // Generate access token
            Date accessTokenExpiry = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION * 1000);
            String accessToken = Jwts.builder()
                    .setSubject(username)
                    .claim("userId", userId) //per me check cili user eshte logged in
                    .claim("role", role) //per me dit ne cfare dashboard me redirect
                    .claim("enabled", enabled) //a eshte i aprovum prej adminit per me pas casje ne dashboard
                    .setIssuedAt(currentDate)
                    .setExpiration(accessTokenExpiry)
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();

            // Generate refresh token
            Date refreshTokenExpiry = new Date(currentDate.getTime() + SecurityConstants.REFRESH_TOKEN_EXPIRATION * 1000);
            String refreshToken = Jwts.builder()
                    .setSubject(username)
                    .claim("userId", userId)
                    .claim("role", role)
                    .claim("enabled", enabled)
                    .setIssuedAt(currentDate)
                    .setExpiration(refreshTokenExpiry)
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();
            refreshTokenService.createRefreshToken(username, refreshToken, refreshTokenExpiry.toInstant());
            return new JwtResponseDto(accessToken, refreshToken);
        }
        public String getUsernameFromJWT(String token){
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }
        public String generateAccessToken(String username,Long userId, String role, boolean enabled) {
            return Jwts.builder()
                    .setSubject(username)
                    .claim("userId", userId)
                    .claim("role", role)
                    .claim("enabled", enabled)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION * 1000))
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();
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
