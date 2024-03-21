package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("users")
    @PreAuthorize("isAuthenticated()")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }
}
