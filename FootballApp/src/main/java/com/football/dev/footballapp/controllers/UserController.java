package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.services.UserService;
import com.football.dev.footballapp.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin("http://localhost:4200")
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @GetMapping("users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }
    @PutMapping("users/{userId}/status")
    public void updateUserStatus(@PathVariable Long userId, @RequestParam boolean enabled) {
        userService.updateUserStatus(userId, enabled);
    }
}
