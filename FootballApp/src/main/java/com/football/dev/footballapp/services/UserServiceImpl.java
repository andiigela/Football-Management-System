package com.football.dev.footballapp.services;

import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
