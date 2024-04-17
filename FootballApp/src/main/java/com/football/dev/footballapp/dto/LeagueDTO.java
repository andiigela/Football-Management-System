package com.football.dev.footballapp.dto;

import java.util.Date;

public record LeagueDTO( Long id ,
                         String name,
                         Date start_date,
                         Date end_date,
                         String description) {
}
