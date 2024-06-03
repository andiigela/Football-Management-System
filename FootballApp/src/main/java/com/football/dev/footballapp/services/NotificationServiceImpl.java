package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.NotificationDto;
import com.football.dev.footballapp.exceptions.UserNotFoundException;
import com.football.dev.footballapp.models.Notification;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.repository.jparepository.UserRepository;
import com.football.dev.footballapp.repository.mongorepository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
            NotificationDto> notificationDtoMapper,UserRepository userRepository
            , AuthenticationHelperService authenticationHelperService){
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
    public Page<NotificationDto> getNotificationsByCurrentUser(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        UserEntity userEntity = authenticationHelperService.getUserEntityFromAuthentication();
        if(userEntity == null) throw new UsernameNotFoundException("User not found!");
        Boolean isAdmin = userEntity.getRole().getDescription().equals("ADMIN_LEAGUE");
        userEntity.setNotificationsNumber(0L);
        userRepository.save(userEntity);
        if(isAdmin){
            Page<Notification> notificationsByUserOrAdmin = notificationRepository.findNotificationsByToUserIdOrSendToAdminTrue(userEntity.getId(),pageRequest)
                        .orElseThrow(() -> new EntityNotFoundException("Notifications not found for Admin League"));
            return notificationsByUserOrAdmin.map(notificationDtoMapper);
        }
        Page<Notification> notificationsByUser = notificationRepository.findNotificationsByToUserIdAndSendToAdminFalse(userEntity.getId(),pageRequest)
                .orElseThrow(() -> new EntityNotFoundException("Notifications not found for simple user with id: " + userEntity.getId()));
        return notificationsByUser.map(notificationDtoMapper);
    }
    @Override
    public Notification createPlayerDeletePermissionNotification(Long playerToBeDeletedId, String message) {
        UserEntity currentUser = authenticationHelperService.getUserEntityFromAuthentication();
        if(currentUser == null) throw new UsernameNotFoundException("User not found!");
        List<UserEntity> adminLeagueUserEntities = userRepository.findUserEntitiesByRoleDescription("ADMIN_LEAGUE");
        if(adminLeagueUserEntities.isEmpty() || adminLeagueUserEntities == null) throw new NullPointerException("adminLeagueUserEntities is null or empty");
        Notification notification = new Notification(playerToBeDeletedId,message);
        notification.setSendToAdmin(true);
        notification.setFromUserId(currentUser.getId());
        notification = notificationRepository.save(notification);
        adminLeagueUserEntities.forEach(admin -> {
            admin.setNotificationsNumber(admin.getNotificationsNumber()+1);
            userRepository.save(admin);
        });
        return notification;
    }
    @Override
    public Long getNotificationsCountByCurrentUser() {
        UserEntity userEntity = authenticationHelperService.getUserEntityFromAuthentication();
        Long notificationCount = userEntity.getNotificationsNumber();
        return notificationCount;
    }

    @Override
    public void updateUserNotificationsCount(Long notificationsCount) {
        UserEntity userEntity = authenticationHelperService.getUserEntityFromAuthentication();
        if(userEntity == null) throw new UserNotFoundException("User not found!");
        userEntity.setNotificationsNumber(notificationsCount);
        userRepository.save(userEntity);
    }
    @Override
    public void deleteNotification(Long userId,NotificationDto notificationDto) {
        Notification notificationDb = notificationRepository.findById(notificationDto.getId()).orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        if((notificationDb.getToUserId() == userId) || (notificationDb.getSendToAdmin())){
            notificationRepository.deleteById(notificationDto.getId());
            return;
        }
        throw new RuntimeException("Notification can not be deleted!");
    }
}
