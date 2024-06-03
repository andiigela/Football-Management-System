package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.League;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Round;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeasonDto {
    private Long id;
    private String name;
    private boolean currentSeason;
    private boolean isDeleted;
//    private LeagueDTO league;

    public SeasonDto(Long id, String name, boolean currentSeason) {
        this.id = id;
        this.name = name;
        this.currentSeason = currentSeason;
//        this.league = league;
    }
    public SeasonDto(String name) {
        this.name = name;

    }
}
