package com.football.dev.footballapp.services;

import com.football.dev.footballapp.models.UserEntity;

public interface AuthenticationHelperService {
    UserEntity getUserEntityFromAuthentication();
}
