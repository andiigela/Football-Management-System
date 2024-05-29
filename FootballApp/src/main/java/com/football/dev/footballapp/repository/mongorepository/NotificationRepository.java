package com.football.dev.footballapp.repository.mongorepository;
import com.football.dev.footballapp.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.*;
public interface NotificationRepository extends MongoRepository<Notification,String> {
    Optional<List<Notification>> findNotificationsByToUserIdAndSendToAdminFalse(Long userId);
    Optional<List<Notification>> findNotificationsByFromUserId(Long userId);
    Optional<List<Notification>> findNotificationsByToUserIdOrSendToAdminTrue(Long userId);
}
