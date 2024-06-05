package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.dto.MatchEventDTO;
import com.football.dev.footballapp.dto.StandingDTO;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Standing;

import java.util.List;
import java.util.Optional;

public interface StandingService {
  void insertStanding( Long seasonId,Long clubId);
  void updateStanding(Long id , StandingDTO standingDTO);
   void insertAllStandings(Long seasonId, List<ClubDto> clubs);

  Optional<StandingDTO> getStandingById(Long id);

  List<StandingDTO> getStandingsForSeason(Long seasonId);

  List<Club> getAllClubsWhereSeasonId(Long id);

  void deleteStanding(Long id);

  void updateStandingsAfterMatch(Long seasonId, Long clubId1, Long clubId2, int goalsScored, int goalsConceded);

  Optional<Standing> getStandingForClub(Long seasonId, Long clubId);
}
