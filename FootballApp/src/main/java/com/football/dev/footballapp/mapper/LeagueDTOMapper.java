package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.models.League;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class LeagueDTOMapper implements Function<League, LeagueDTO> {
    @Override
    public LeagueDTO apply(League league) {
        return new LeagueDTO(league.getId(), league.getName(),league.getStart_date(),league.getEnd_date(), league.getDescription());
    }
}
