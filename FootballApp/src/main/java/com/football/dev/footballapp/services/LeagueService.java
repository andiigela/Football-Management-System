package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.LeagueDTO;

import java.util.List;
import java.util.Optional;

public interface LeagueService {
    void insertLeague(LeagueDTO leagueDTO);
    Optional<LeagueDTO> selectLeagueByName(String name);
    Optional<LeagueDTO> selectLeagueById(Long id);
    List<LeagueDTO> listAllLeagues();
    void deleteLeague(Long id);
    void updateLeague(Long id , LeagueDTO leagueDTO);


}
