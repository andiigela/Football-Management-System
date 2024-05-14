package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Stadium;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO{
    private Long id;
    private ClubDto homeTeamId;
    private ClubDto awayTeamId;
    private LocalDateTime matchDate;
    private Stadium stadium;
    private String result;
    private Integer homeTeamScore;
    private Integer awayTeamScore;

}
