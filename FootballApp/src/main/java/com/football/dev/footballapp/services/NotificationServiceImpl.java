package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.NotificationDto;
import com.football.dev.footballapp.models.Notification;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.repository.jparepository.UserRepository;
import com.football.dev.footballapp.repository.mongorepository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    private final Function<Notification,NotificationDto> notificationDtoMapper;
    private final UserRepository userRepository;
    public NotificationServiceImpl(NotificationRepository notificationRepository,Function<Notification,
            NotificationDto> notificationDtoMapper,UserRepository userRepository){
        this.notificationRepository=notificationRepository;
        this.notificationDtoMapper=notificationDtoMapper;
        this.userRepository=userRepository;
    }
    @Override
    public void createNotification(String message) {
        List<UserEntity> adminLeagueUserEntities = userRepository.findUserEntitiesByRoleDescription("ADMIN_LEAGUE");
        if(adminLeagueUserEntities.isEmpty() || adminLeagueUserEntities == null) throw new NullPointerException("adminLeagueUserEntities is null or empty");
        adminLeagueUserEntities.forEach(user -> {
            Notification notification = new Notification(message);
            notification.setUserId(user.getId());
            notificationRepository.save(notification);
        });
    }
    @Override
    public List<NotificationDto> getNotificationsByUser(Long userId) {
        return notificationRepository.findNotificationsByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Notifications not found with userId: " + userId))
                .stream().map(notificationDtoMapper).collect(Collectors.toList());
    }
}
