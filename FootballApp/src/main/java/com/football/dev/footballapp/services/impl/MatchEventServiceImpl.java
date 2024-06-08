package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.MatchEventDTO;
import com.football.dev.footballapp.dto.MatchEventRequest;
import com.football.dev.footballapp.mapper.MatchEventDTOMapper;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.MatchEvent;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.models.enums.TypeStatus;
import com.football.dev.footballapp.repository.jparepository.ClubRepository;
import com.football.dev.footballapp.repository.jparepository.MatchEventRepository;
import com.football.dev.footballapp.repository.jparepository.MatchRepository;
import com.football.dev.footballapp.repository.jparepository.PlayerRepository;
import com.football.dev.footballapp.services.MatchEventService;
import com.football.dev.footballapp.services.MatchService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchEventServiceImpl implements MatchEventService {
  private final MatchEventRepository matchEventRepository;

  private final MatchEventDTOMapper matchEventDTOMapper;

  private final ClubRepository clubRepository;

  private final MatchRepository matchRepository;

  private final PlayerRepository playerRepository;
  private final MatchService matchService;

  public MatchEventServiceImpl(MatchEventRepository matchEventRepository, MatchEventDTOMapper matchEventDTOMapper, ClubRepository clubRepository, MatchRepository matchRepository, PlayerRepository playerRepository, MatchService matchService) {
    this.matchEventRepository = matchEventRepository;
    this.matchEventDTOMapper = matchEventDTOMapper;
    this.clubRepository = clubRepository;
    this.matchRepository = matchRepository;
    this.playerRepository = playerRepository;
    this.matchService = matchService;
  }

  @Override
  public void insertMatchEvent(Long id ,MatchEventRequest matchEventRequest) {
    Club club =clubRepository.findById(matchEventRequest.club_id()).orElseThrow(()-> new EntityNotFoundException("Club not found "));
    Player player = playerRepository.findById(matchEventRequest.player_id()).orElseThrow(()-> new EntityNotFoundException("Player not found "));
    Player assisted2 = null;
    Player sub2= null;

    if (matchEventRequest.assist_id()!=null){
      Player assisted = playerRepository.findById(matchEventRequest.assist_id()).orElseThrow(()-> new EntityNotFoundException("assister not found "));
      assisted2=assisted;

    }
    if (matchEventRequest.subsitutePlayer_id()!=null){
      Player sub = playerRepository.findById(matchEventRequest.subsitutePlayer_id()).orElseThrow(()-> new EntityNotFoundException("Club not found "));
      sub2=sub;
    }

    if (matchEventRequest.type()==TypeStatus.GOAL){
      if(matchEventRequest.isOwnGoal()){
        matchService.ownGoalScored(id,matchEventRequest.club_id());
        return;
      }
      matchService.goalScored(id,matchEventRequest.club_id());

    }
    Match match = matchRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Match  not found "));;

    MatchEvent matchEvent = new MatchEvent(matchEventRequest.minute(),
      matchEventRequest.type(),
      club,
      player,
      assisted2,
      sub2,
      matchEventRequest.isPenalty(),
      matchEventRequest.isOwnGoal(),
      matchEventRequest.isRedCard(),
      matchEventRequest.isYellowCard(),
      matchEventRequest.description(),
      match);

    matchEventRepository.save(matchEvent);
  }

  @Override
  public void updateMatchEvent(Long id,MatchEventRequest matchEventRequest) {

    Club club =clubRepository.findById(matchEventRequest.club_id()).get();
    Player player = playerRepository.findById(matchEventRequest.player_id()).get();
    Player assisted = playerRepository.findById(matchEventRequest.assist_id()).get();
    Player sub = playerRepository.findById(matchEventRequest.subsitutePlayer_id()).get();

    matchEventRepository.findById(id).ifPresent(dbMatchEvent ->{
      dbMatchEvent.setType(matchEventRequest.type());
      dbMatchEvent.setClub(club);
      dbMatchEvent.setPlayer(player);
      dbMatchEvent.setAssisted(assisted);
      dbMatchEvent.setSubstitutePlayer(sub);
      dbMatchEvent.setPenalty(matchEventRequest.isPenalty());
      dbMatchEvent.setOwnGoal(matchEventRequest.isOwnGoal());
      dbMatchEvent.setRedCard(matchEventRequest.isRedCard());
      dbMatchEvent.setYellowCard(matchEventRequest.isYellowCard());
      dbMatchEvent.setDescription(matchEventRequest.description());

    });

  }


  @Override
  public Optional<MatchEventDTO> getMatchEventById(Long id) {
    return Optional.empty();
  }

  @Override
  public List<MatchEventDTO> getMatchEventsForMatch(Long matchId) {
  List<MatchEvent> matchEvents = matchEventRepository.findAllByMatchId(matchId);
    List<MatchEventDTO> dtos = matchEventRepository.findAllByMatchId(matchId).stream().map(matchEventDTOMapper).collect(Collectors.toList());
    Match match = matchRepository.findById(matchId).get();
    return dtos;

  }

  @Override
  public void deleteMatchEvent(Long match_id ,Long id) {
    MatchEvent matchEvent = matchEventRepository.findById(id).get();
    matchEvent.setType(TypeStatus.REMOVED);
    matchEvent.setClub(null);
    matchEvent.setAssisted(null);

  }

  @Override
  public List<MatchEventDTO> getMatchEventsForPlayer(Long playerId) {
    return null;
  }
}
