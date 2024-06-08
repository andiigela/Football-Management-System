package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.dto.StandingDTO;
import com.football.dev.footballapp.mapper.SeasonDtoMapper;
import com.football.dev.footballapp.mapper.StandingDTOMapper;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Season;
import com.football.dev.footballapp.models.Standing;
import com.football.dev.footballapp.repository.jparepository.ClubRepository;
import com.football.dev.footballapp.repository.jparepository.SeasonRepository;
import com.football.dev.footballapp.repository.jparepository.StandingRepository;
import com.football.dev.footballapp.services.SeasonService;
import com.football.dev.footballapp.services.StandingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StandingServiceImpl implements StandingService {
  private final  StandingRepository standingRepository;
  private final ClubRepository clubRepository;
  private final SeasonRepository seasonRepository;

  public StandingServiceImpl(StandingRepository standingRepository, ClubRepository clubRepository, SeasonRepository seasonRepository, StandingDTOMapper standingDTOMapper) {
    this.standingRepository = standingRepository;
    this.clubRepository = clubRepository;
    this.seasonRepository = seasonRepository;
    this.standingDTOMapper = standingDTOMapper;
  }

  private final StandingDTOMapper standingDTOMapper;


  @Override
  public void insertStanding( Long seasonId,Long clubId) {
    Season season = seasonRepository.findById(seasonId).get();
    Club club = clubRepository.findById(clubId).get();
    standingRepository.save(new Standing(club,0,0,0,0,0,0,0,season));
  }

  public void insertAllStandings(Long seasonId, List<ClubDto> clubs){
    clubs.stream().forEach(clubDto -> insertStanding(seasonId,clubDto.getId()));

  }
  @Override
  public void updateStanding(Long id, StandingDTO standingDTO) {
    Standing standing = standingRepository.findById(id).get();

    standing.setMatchesPlayed(standingDTO.matchesPlayed());
    standing.setWonMatches(standing.wonMatches);
    standing.setDrawMatches(standing.drawMatches);
    standing.setLostMatches(standing.lostMatches);
    standing.setGoalScored(standing.goalScored);
    standing.setGoalConceded(standing.goalConceded);
    standing.setPoints(standing.points);

    standingRepository.save(standing);

  }

  @Override
  public Optional<StandingDTO> getStandingById(Long id) {
    Optional<Standing> standingOptional = standingRepository.findById(id);
    return standingOptional.map(standingDTOMapper);
  }


  @Override
  public List<StandingDTO> getStandingsForSeason(Long seasonId) {
    return standingRepository.findAllBySeasonId(seasonId).stream().map(standingDTOMapper).collect(Collectors.toList());
  }

  @Override
  public List<Club> getAllClubsWhereSeasonId(Long id) {
    return standingRepository.findAllClubsBySeasonId(id);
  }

  @Override
  public void deleteStanding(Long id) {
    Standing standing = standingRepository.findById(id).get();
    standing.setIsDeleted(true);
    standingRepository.save(standing);
  }

  @Override
  public void updateStandingsAfterMatch(Long seasonId, Long homeClubId,Long awayClubId,  int goalsHomeTeam, int goalsAwayTeam) {
      Standing standingHome = standingRepository.findBySeasonIdAndClubId(seasonId,homeClubId).get();
      Standing standingAway = standingRepository.findBySeasonIdAndClubId(seasonId,awayClubId).get();

      if(goalsHomeTeam>goalsAwayTeam){
        standingHome.setWonMatches(standingHome.getWonMatches()+1);
        standingAway.setLostMatches(standingAway.getLostMatches()+1);
        standingHome.setPoints(standingHome.getPoints()+3);

      } else if (goalsHomeTeam<goalsAwayTeam) {
        standingAway.setWonMatches(standingAway.getWonMatches()+1);
        standingHome.setLostMatches(standingHome.getLostMatches()+1);
        standingAway.setPoints(standingAway.getPoints()+3);


      }else{

        standingHome.setDrawMatches(standingHome.getDrawMatches()+1);
        standingAway.setDrawMatches(standingAway.getDrawMatches()+1);
        standingHome.setPoints(standingHome.getPoints()+1);
        standingAway.setPoints(standingAway.getPoints()+1);
      }
      standingAway.setGoalScored(standingAway.getGoalScored()+goalsAwayTeam);
      standingAway.setGoalConceded(standingAway.getGoalConceded()+goalsAwayTeam);
      standingHome.setGoalScored(standingHome.getGoalScored()+goalsHomeTeam);
      standingHome.setGoalConceded(standingHome.getGoalConceded()+goalsAwayTeam);

  }


  @Override
  public Optional<Standing> getStandingForClub(Long seasonId, Long clubId) {
    return standingRepository.findBySeasonIdAndClubId(seasonId,clubId);
  }
}
