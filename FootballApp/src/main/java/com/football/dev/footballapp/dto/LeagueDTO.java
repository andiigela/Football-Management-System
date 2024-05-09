package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.Season;

import java.util.Date;
import java.util.List;

public record LeagueDTO( Long id ,
                         String name,
                         Date start_date,
                         Date end_date,
                         String description,
                         List<Season> seasons) {
}
