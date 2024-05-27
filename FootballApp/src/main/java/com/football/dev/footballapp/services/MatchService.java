package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.dto.RoundDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MatchService {

    void saveMatch(MatchDTO matchDto, Long roundId);
    Page<MatchDTO> retrieveMatches(Long matchId, int pageNumber, int pageSize);
    MatchDTO getMatch(Long roundId,Long matchId);
    void updateMatch(MatchDTO matchDto, Long matchId, Long roundId);
    void deleteMatch(Long matchId, Long roundId);
//    void insertMatch(MatchDTO MatchDTO);
//    Optional<MatchDTO> selectMatchById(Long id);
//    List<MatchDTO> listAllMatch();
//    void deleteMatch(Long id);
//    void updateMatch(Long id , MatchDTO matchDTO);

}
