package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.SeasonDto;

import java.util.List;
import java.util.Optional;

public interface LeagueService {
    void insertLeague(LeagueDTO leagueDTO);
    Optional<LeagueDTO> selectLeagueByName(String name);
    Optional<LeagueDTO> selectLeagueById(Long id);
    List<LeagueDTO> listAllLeagues();
    void deleteLeague(Long id);
    void updateLeague(Long id , LeagueDTO leagueDTO);
    void createSeasonForLeague(Long leagueId, SeasonDto seasonDto);

}
