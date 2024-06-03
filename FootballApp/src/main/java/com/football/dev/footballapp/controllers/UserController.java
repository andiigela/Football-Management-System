package com.football.dev.footballapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.dev.footballapp.dto.UserEntityDto;
import com.football.dev.footballapp.models.ES.LeagueES;
import com.football.dev.footballapp.models.ES.UserEntityES;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.security.JWTGenerator;
import com.football.dev.footballapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin("http://localhost:4200")
public class UserController {
    private final UserService userService;
    private final JWTGenerator jwtGenerator;
    private final ObjectMapper objectMapper;


    public UserController(UserService userService, JWTGenerator jwtGenerator,ObjectMapper objectMapper){
        this.userService=userService;
        this.jwtGenerator = jwtGenerator;
        this.objectMapper=objectMapper;
    }
    @GetMapping("users")
    @PreAuthorize("hasRole('ADMIN_LEAGUE')")
    public ResponseEntity<Page<UserEntityDto>> getUsersWithUserRole(@RequestParam(defaultValue = "0") int pageNumber,
                                                                 @RequestParam(defaultValue = "3") int pageSize) {
        Page<UserEntityDto> userEntityDtoPage = userService.getUsersByRoleAndIsDeleted("ADMIN_CLUB", false, pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(userEntityDtoPage);
    }

    @PutMapping("users/{userId}/status")
    public void updateUserStatus(@PathVariable Long userId, @RequestParam boolean enabled) {
        userService.updateUserStatus(userId, enabled);
    }
    @DeleteMapping("users/delete/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("users/save")
    public ResponseEntity<Void> saveUser(@RequestBody UserEntityDto userDto) {
        userService.saveUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("users/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserEntityDto updatedUserDto, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        String token = authorizationHeader.substring(7);

        if (!jwtGenerator.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        userService.updateUser(userId, updatedUserDto);
        return ResponseEntity.ok().build();
    }


    @GetMapping("users/{userId}")
    public UserEntity getUserProfile(@PathVariable Long userId) {
        return userService.getUserProfile(userId);
    }
    @GetMapping("users/search")
    public ResponseEntity<List<UserEntityES>> searchUsers(@RequestParam String email) {
        System.out.println("Searching users with email: " + email);
        List<UserEntityES> users = userService.findUsersByEmailES(email);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

}

