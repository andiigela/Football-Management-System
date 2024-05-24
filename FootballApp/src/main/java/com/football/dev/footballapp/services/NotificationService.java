package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.NotificationDto;
import com.football.dev.footballapp.models.Notification;

import java.util.List;

public interface NotificationService {
    void createNotification(String message);
    void createPlayerDeletePermissionNotification(Notification notification);
    List<NotificationDto> getNotificationsByUser(Long userId);
    List<NotificationDto> getNotificationsSentFromCurrentUser();
}
