package com.football.dev.footballapp.services;

import com.football.dev.footballapp.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public interface UserService {
    public List<UserEntity> users = new ArrayList<>();
}
