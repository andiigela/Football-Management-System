package com.football.dev.footballapp.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private String id;
    private Long playerId;
    private String description;
}
