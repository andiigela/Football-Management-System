package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Season;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class SeasonDtoMapper implements Function<Season, SeasonDto> {

    @Override
    public SeasonDto apply(Season season) {
        return new SeasonDto(
                 season.getId(),
                season.getName(),
          season.getCurrentSeason(),
          season.getStart_date(),
          season.getEnd_date(),
          season.getHeadToHead().intValue(),
          season.getNumberOfStanding().intValue()
        );
    }
}


