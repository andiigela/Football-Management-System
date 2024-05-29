package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.NotificationDto;
import com.football.dev.footballapp.models.Notification;

import java.util.List;

public interface NotificationService {
    void createNotification(String message);
    Notification createPlayerDeletePermissionNotification(Long playerToBeDeletedId, String message);
    List<NotificationDto> getNotificationsByCurrentUser();
    Long getNotificationsCountByCurrentUser();
    void updateUserNotificationsCount(Long notificationsCount);
}
