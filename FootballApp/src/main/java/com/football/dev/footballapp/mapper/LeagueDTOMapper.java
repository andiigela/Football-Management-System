package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.models.League;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LeagueDTOMapper implements Function<League, LeagueDTO> {
    @Override
    public LeagueDTO apply(League league) {
        return new LeagueDTO(league.getId(), league.getName(),league.getFounded(),league.getDescription(), league.getPicture());
    }
}
