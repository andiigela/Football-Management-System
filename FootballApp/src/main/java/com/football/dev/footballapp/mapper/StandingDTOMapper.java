package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.StandingDTO;
import com.football.dev.footballapp.models.Standing;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class StandingDTOMapper implements Function<Standing,StandingDTO> {

  private final ClubToDTOMapper clubToDTOMapper;

  private final SeasonDtoMapper seasonDtoMapper;

  public StandingDTOMapper(ClubToDTOMapper clubToDTOMapper, SeasonDtoMapper seasonDtoMapper) {
    this.clubToDTOMapper = clubToDTOMapper;
    this.seasonDtoMapper = seasonDtoMapper;
  }

  @Override
  public StandingDTO apply(Standing standing) {
    return new StandingDTO(standing.getId(), clubToDTOMapper.apply(standing.getClub()),standing.getMatchesPlayed(),standing.getWonMatches(), standing.getDrawMatches(), standing.getLostMatches(),standing.getGoalScored(),standing.getGoalConceded(),standing.getPoints(),seasonDtoMapper.apply(standing.getSeason()));
  }
}
