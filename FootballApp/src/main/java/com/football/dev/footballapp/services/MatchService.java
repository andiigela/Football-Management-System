package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.MatchDTO;

import java.util.List;
import java.util.Optional;

public interface MatchService {
    void insertMatch(MatchDTO MatchDTO);
    Optional<MatchDTO> selectMatchById(Long id);
    List<MatchDTO> listAllMatch();
    void deleteMatch(Long id);
    void updateMatch(Long id , MatchDTO matchDTO);

}
