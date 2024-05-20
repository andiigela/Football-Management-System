package com.football.dev.footballapp.repository.mongorepository;

import com.football.dev.footballapp.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification,String> {
}
