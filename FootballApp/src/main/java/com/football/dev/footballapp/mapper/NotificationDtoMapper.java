package com.football.dev.footballapp.mapper;
import com.football.dev.footballapp.dto.NotificationDto;
import com.football.dev.footballapp.models.Notification;
import org.springframework.stereotype.Service;
import java.util.function.Function;
@Service
public class NotificationDtoMapper implements Function<Notification, NotificationDto> {
    @Override
    public NotificationDto apply(Notification notification) {
        return new NotificationDto(notification.getId(),notification.getPlayerId(),notification.getDescription());
    }
}
