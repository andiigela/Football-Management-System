package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.UserEntityDto;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.UserEntity;

import java.util.ArrayList;
import java.util.List;


public interface UserService {
    List<UserEntity> getAllUsers();
    void updateUserStatus(Long userId, boolean enabled);
    void deleteUser(Long userId);
    List<UserEntity> getUsersByRole(String role);
    void saveUser(UserEntityDto user);
    void updateUser(Long userId, UserEntityDto updatedUser);
    UserEntity getUserProfile(Long userId);

}
