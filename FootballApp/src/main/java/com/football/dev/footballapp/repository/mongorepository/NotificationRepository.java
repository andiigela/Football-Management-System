package com.football.dev.footballapp.repository.mongorepository;
import com.football.dev.footballapp.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.*;
public interface NotificationRepository extends MongoRepository<Notification,String> {
    Optional<Page<Notification>> findNotificationsByToUserIdAndSendToAdminFalse(Long userId, Pageable pageable);
    Optional<List<Notification>> findNotificationsByFromUserId(Long userId);
    Optional<Page<Notification>> findNotificationsByToUserIdOrSendToAdminTrue(Long userId, Pageable pageable);
}
