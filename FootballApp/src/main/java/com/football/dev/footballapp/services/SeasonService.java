package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Season;


import java.util.List;
import java.util.Optional;

public interface SeasonService {
    void saveSeason(SeasonDto seasonDto);
    List<SeasonDto> getAllSeasons();
    //Optional<SeasonDto> getSeasonById(Long id);
    void updateSeason(Long id, SeasonDto seasonDto);
    void deleteSeason(Long id);
    List<Club> getClubsBySeasonId(Long seasonId);
//    List<String> getAwayClubBySeasonId(Long seasonId);
//    List<String> getHomeClubBySeasonId(Long seasonId);
//    List<String> getStadiumBySeasonId(Long seasonId);
}
