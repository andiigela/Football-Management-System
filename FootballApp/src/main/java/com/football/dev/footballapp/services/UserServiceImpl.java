package com.football.dev.footballapp.services;

import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public void updateUserStatus(Long userId, boolean enabled) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(enabled);
        userRepository.save(user);
    }
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete user with ID: " + userId, e);
        }
    }
}
