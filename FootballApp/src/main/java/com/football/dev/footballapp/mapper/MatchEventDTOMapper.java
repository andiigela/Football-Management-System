package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.dto.MatchEventDTO;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.MatchEvent;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class MatchEventDTOMapper implements Function<MatchEvent, MatchEventDTO> {

  private final PlayerToDTOMapper playerToDTOMapper;
  private final ClubToDTOMapper clubToDTOMapper;
  private final MatchDTOMapper matchDTOMapper;

  public MatchEventDTOMapper(PlayerToDTOMapper playerToDTOMapper, ClubToDTOMapper clubToDTOMapper, MatchDTOMapper matchDTOMapper) {
    this.playerToDTOMapper = playerToDTOMapper;
    this.clubToDTOMapper = clubToDTOMapper;
    this.matchDTOMapper = matchDTOMapper;
  }

  @Override
  public MatchEventDTO apply(MatchEvent matchEvent) {

    MatchDTO matchDTO =matchDTOMapper.apply(matchEvent.getMatch());
    PlayerDto playerDto = playerToDTOMapper.apply(matchEvent.getPlayer());
    PlayerDto asistDto = null;
    PlayerDto subDto = null;
    if(matchEvent.getAssisted()!=null){
      asistDto = playerToDTOMapper.apply(matchEvent.getAssisted());
    }
    if(matchEvent.getSubstitutePlayer()!=null){
      subDto = playerToDTOMapper.apply(matchEvent.getSubstitutePlayer());
    }
    ClubDto clubDto = clubToDTOMapper.apply(matchEvent.getClub());

    return new MatchEventDTO(matchEvent.getId(), matchEvent.getMinute(),matchEvent.getType(),clubDto,playerDto,asistDto,subDto, matchEvent.isPenalty(), matchEvent.isOwnGoal(),matchEvent.isRedCard(),matchEvent.isYellowCard(),matchEvent.getDescription(),matchDTO);
  }
}
