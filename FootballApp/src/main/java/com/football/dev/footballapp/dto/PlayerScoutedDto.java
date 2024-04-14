package com.football.dev.footballapp.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerScoutedDto {
    private String playerName;
    private String playerSurname;
    private int playerAge;
    private double playerWeight;
    private double playerHeight;
    private String playerPosition;
    private List<InjuriesDto> injuries;
    private String report;
}
