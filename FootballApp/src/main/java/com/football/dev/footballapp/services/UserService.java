package com.football.dev.footballapp.services;

import com.football.dev.footballapp.models.UserEntity;

import java.util.ArrayList;
import java.util.List;


public interface UserService {
    List<UserEntity> getAllUsers();
}
