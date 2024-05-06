package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.models.Match;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class MatchDTOMapper implements Function<Match, MatchDTO> {
    @Override
    public MatchDTO apply(Match match) {
        return new MatchDTO(match.getId(),match.getHomeTeamId(),match.getAwayTeamId(),match.getMatchDate(),match.getStadium(),match.getResult(), match.getHomeTeamScore(), match.getAwayTeamScore(), match.getSeason());
    }
}
