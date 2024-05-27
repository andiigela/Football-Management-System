package com.football.dev.footballapp.controllers;
import com.football.dev.footballapp.dto.NotificationDto;
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
    public NotificationsController(NotificationService notificationService){
        this.notificationService=notificationService;
    }
    @GetMapping("/")
    public ResponseEntity<List<NotificationDto>> getNotifications() {
        List<NotificationDto> notificationDtoList = this.notificationService.getNotificationsByCurrentUser();
        return ResponseEntity.status(HttpStatus.OK).body(notificationDtoList);
    }
    @GetMapping("/counts")
    public ResponseEntity<Long> getNotificationCount() {
        return ResponseEntity.ok(this.notificationService.getNotificationsCountByCurrentUser());
    }
    @PutMapping("/counts/update")
    public ResponseEntity<String> updatePlayerNotificationsCount(@RequestBody Long notificationCount) {
        this.notificationService.updateUserNotificationsCount(notificationCount);
        return ResponseEntity.ok().build();
    }
}
