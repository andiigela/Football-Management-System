package com.football.dev.footballapp.services.impl;
import com.football.dev.footballapp.models.RefreshToken;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.repository.jparepository.RefreshTokenRepository;
import com.football.dev.footballapp.repository.jparepository.UserRepository;
import com.football.dev.footballapp.services.RefreshTokenService;
import org.springframework.stereotype.Service;
import java.time.Instant;
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository,UserRepository userRepository){
        this.refreshTokenRepository=refreshTokenRepository;
        this.userRepository=userRepository;
    }
    public RefreshToken createRefreshToken(String username,String token, Instant expiryDate){
        UserEntity user = userRepository.findByEmail(username).get();
        if(user == null) return null;
        RefreshToken existingRefreshDb = this.refreshTokenRepository.findByUserInfo(user);
        if(existingRefreshDb == null){
            RefreshToken newRefreshToken = new RefreshToken();
            newRefreshToken.setToken(token);
            newRefreshToken.setExpiryDate(expiryDate);
            newRefreshToken.setUserInfo(user);
            refreshTokenRepository.save(newRefreshToken);
            return newRefreshToken;
        }
        existingRefreshDb.setToken(token);
        existingRefreshDb.setExpiryDate(expiryDate);

        refreshTokenRepository.save(existingRefreshDb);
        return existingRefreshDb;
    }
    public RefreshToken findByToken(String token){
        return this.refreshTokenRepository.findByToken(token);
    }
    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return refreshToken;
    }
    public String getRefreshTokenByUsername(String username) {
        UserEntity user = userRepository.findByEmail(username).orElse(null);
        if (user == null) return null;
        RefreshToken refreshToken = refreshTokenRepository.findByUserInfo(user);
        return (refreshToken != null) ? refreshToken.getToken() : null;
    }
}
