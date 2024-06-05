package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.models.Season;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SeasonService {
    Long  saveSeason(SeasonDto seasonDto, Long leagueId);
     Page<SeasonDto> retrieveSeasons(Long leagueId, int pageNumber, int pageSize);
     SeasonDto getSeason(Long leagueId,Long seasonId);
     void updateSeason(SeasonDto seasonDto, Long seasonId, Long leagueId);
     void deleteSeason(Long seasonId, Long leagueId);
      void generateRoundsAndMatches(Long id );
//    void saveSeason(SeasonDto seasonDto);
//    void updateSeason(Long id, SeasonDto seasonDto);
//    Optional<SeasonDto> getSeasonById(Long id);
//    List<SeasonDto> getAllSeasons();
//    void deleteSeason(Long id);
//    void createRoundForSeason(Long seasonId, RoundDto roundDto) throws ResourceNotFoundException;
//    List<RoundDto> getRoundsWithMatchesForSeason(Long seasonId);
}
