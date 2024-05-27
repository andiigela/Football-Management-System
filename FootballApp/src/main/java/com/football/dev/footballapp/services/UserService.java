package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.UserEntityDto;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

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
    Page<UserEntityDto> getUsersByRoleAndIsDeleted(String role, boolean isDeleted, int pageNumber, int pageSize);
    UserEntity getUserByEmail(String email);
}
