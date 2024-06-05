package com.football.dev.footballapp.controllers;
import com.football.dev.footballapp.dto.NotificationDto;
import com.football.dev.footballapp.dto.PageResponseDto;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.services.NotificationService;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<PageResponseDto<NotificationDto>>getNotifications(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        Page<NotificationDto> notificationDtoPage = this.notificationService.getNotificationsByCurrentUser(page,size);
        PageResponseDto<NotificationDto> responseDto = new PageResponseDto<>(
                notificationDtoPage.getContent(),
                notificationDtoPage.getNumber(),
                notificationDtoPage.getSize(),
                notificationDtoPage.getTotalElements()
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
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
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<String> deletePlayerNotification(@PathVariable("userId") Long userId,@RequestBody NotificationDto notificationDto) {
        this.notificationService.deleteNotification(userId,notificationDto);
        return ResponseEntity.ok().build();
    }
}
