package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.Season;
import lombok.Setter;

import java.util.Date;
import java.util.List;


public record LeagueDTO( Long id ,
                         String name,
                         Integer founded,
                         String description,
                         String picture) {

}
