package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.models.Match;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class MatchDTOMapper implements Function<Match, MatchDTO> {
    @Override
    public MatchDTO apply(Match match) {
        ClubDto homeTeamDto = new ClubDto(match.getHomeTeamId().getId(), match.getHomeTeamId().getName(), match.getHomeTeamId().getFoundedYear(), match.getHomeTeamId().getCity(), match.getHomeTeamId().getWebsite());
        ClubDto awayTeamDto = new ClubDto(match.getAwayTeamId().getId(), match.getAwayTeamId().getName(), match.getAwayTeamId().getFoundedYear(), match.getAwayTeamId().getCity(), match.getAwayTeamId().getWebsite());

        return new MatchDTO(
                match.getId(),
                homeTeamDto,
                awayTeamDto,
//                match.getHomeTeamId(),
//                match.getAwayTeamId(),
                match.getMatchDate(),
//                match.getStadium(),
                match.getResult(),
                match.getHomeTeamScore(),
                match.getAwayTeamScore(),
          match.getMatchStatus()
        );
    }
}
