package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Season;
import com.football.dev.footballapp.models.Stadium;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

public record MatchDTO(Long id ,
                       Club homeTeamId,
                       Club awayTeamId,
                       LocalDateTime matchDate,
                       Stadium stadium,
                       String result,
                       Integer homeTeamScore,
                       Integer awayTeamScore,
                       Season season) {
}
