package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.NotificationDto;
import com.football.dev.footballapp.models.Notification;
import org.springframework.data.domain.Page;
public interface NotificationService {
    void createNotification(String message);
    Notification createPlayerDeletePermissionNotification(Long playerToBeDeletedId, String message);
    Page<NotificationDto> getNotificationsByCurrentUser(int pageNumber, int pageSize);
    Long getNotificationsCountByCurrentUser();
    void updateUserNotificationsCount(Long notificationsCount);
    void deleteNotification(Long userId,NotificationDto notificationDto);
}
