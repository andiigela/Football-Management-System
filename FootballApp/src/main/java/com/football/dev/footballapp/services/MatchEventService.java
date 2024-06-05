package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.MatchEventDTO;
import com.football.dev.footballapp.dto.MatchEventRequest;
import com.football.dev.footballapp.models.MatchEvent;

import java.util.List;
import java.util.Optional;

public interface MatchEventService {

  void insertMatchEvent(Long match_id ,MatchEventRequest request);

  Optional<MatchEventDTO> getMatchEventById(Long id);

  List<MatchEventDTO> getMatchEventsForMatch(Long matchId);

  void deleteMatchEvent(Long match_id, Long id);

  List<MatchEventDTO> getMatchEventsForPlayer(Long playerId);
  void updateMatchEvent(Long id , MatchEventRequest matchEventRequest);
}
