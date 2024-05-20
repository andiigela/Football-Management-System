package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.RefreshToken;
import com.football.dev.footballapp.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    RefreshToken findByToken(String token);
    RefreshToken findByUserInfo(UserEntity user);
}
