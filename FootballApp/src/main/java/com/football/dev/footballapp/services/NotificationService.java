package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    void createNotification(String message);
    List<NotificationDto> getNotificationsByUser(Long userId);
}
