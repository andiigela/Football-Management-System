package com.football.dev.footballapp.controllers;
import com.football.dev.footballapp.dto.NotificationDto;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.services.AuthenticationHelperService;
import com.football.dev.footballapp.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("http://localhost:4200")
public class NotificationsController {
    private final NotificationService notificationService;
    private final AuthenticationHelperService authenticationHelperService;
    public NotificationsController(NotificationService notificationService,AuthenticationHelperService authenticationHelperService){
        this.notificationService=notificationService;
        this.authenticationHelperService=authenticationHelperService;
    }
    @GetMapping("/")
    public ResponseEntity<List<NotificationDto>> getNotifications() {
        UserEntity userEntity = authenticationHelperService.getUserEntityFromAuthentication();
        List<NotificationDto> notificationDtoList = this.notificationService.getNotificationsByUser(userEntity.getId());
        return ResponseEntity.status(HttpStatus.OK).body(notificationDtoList);
    }
}
