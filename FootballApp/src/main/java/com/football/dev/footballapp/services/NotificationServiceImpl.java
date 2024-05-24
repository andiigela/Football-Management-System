package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.NotificationDto;
import com.football.dev.footballapp.models.Notification;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.repository.jparepository.UserRepository;
import com.football.dev.footballapp.repository.mongorepository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    private final Function<Notification,NotificationDto> notificationDtoMapper;
    private final UserRepository userRepository;
    private final AuthenticationHelperService authenticationHelperService;
    public NotificationServiceImpl(NotificationRepository notificationRepository,Function<Notification,
            NotificationDto> notificationDtoMapper,UserRepository userRepository,AuthenticationHelperService authenticationHelperService){
        this.notificationRepository=notificationRepository;
        this.notificationDtoMapper=notificationDtoMapper;
        this.userRepository=userRepository;
        this.authenticationHelperService=authenticationHelperService;
    }
    @Override
    public void createNotification(String message) {
        UserEntity currentUser = authenticationHelperService.getUserEntityFromAuthentication();
        if(currentUser == null) throw new UsernameNotFoundException("User not found!");
        List<UserEntity> adminLeagueUserEntities = userRepository.findUserEntitiesByRoleDescription("ADMIN_LEAGUE");
        if(adminLeagueUserEntities.isEmpty() || adminLeagueUserEntities == null) throw new NullPointerException("adminLeagueUserEntities is null or empty");
        adminLeagueUserEntities.forEach(user -> {
            Notification notification = new Notification(message);
            notification.setToUserId(user.getId());
            notification.setFromUserId(currentUser.getId());
            user.setNotificationsNumber(user.getNotificationsNumber()+1);
            notificationRepository.save(notification);
            userRepository.save(user);
        });
    }
    @Override
    public List<NotificationDto> getNotificationsByUser(Long userId) {
        UserEntity userDb = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User not found with id: " + userId));
        userDb.setNotificationsNumber(0L);
        userRepository.save(userDb);
        return notificationRepository.findNotificationsByToUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Notifications not found with userId: " + userId))
                .stream().map(notificationDtoMapper).collect(Collectors.toList());
    }
    @Override
    public void createPlayerDeletePermissionNotification(Notification notificationParam) {
        UserEntity currentUser = authenticationHelperService.getUserEntityFromAuthentication();
        if(currentUser == null) throw new UsernameNotFoundException("User not found!");
        List<UserEntity> adminLeagueUserEntities = userRepository.findUserEntitiesByRoleDescription("ADMIN_LEAGUE");
        if(adminLeagueUserEntities.isEmpty() || adminLeagueUserEntities == null) throw new NullPointerException("adminLeagueUserEntities is null or empty");
        adminLeagueUserEntities.forEach(user -> {
            notificationParam.setToUserId(user.getId());
            notificationParam.setFromUserId(currentUser.getId());
            user.setNotificationsNumber(user.getNotificationsNumber()+1);
            notificationRepository.save(notificationParam);
            userRepository.save(user);
        });
    }
    @Override
    public List<NotificationDto> getNotificationsSentFromCurrentUser() {
        UserEntity currentUser = authenticationHelperService.getUserEntityFromAuthentication();
        return notificationRepository.findNotificationsByFromUserId(currentUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("Sent notifications not found with userId: " + currentUser.getId()))
                .stream().map(notificationDtoMapper).collect(Collectors.toList());
    }
}
