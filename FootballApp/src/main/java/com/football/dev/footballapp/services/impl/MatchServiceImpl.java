package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.mapper.MatchDTOMapper;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.repository.MatchRepository;
import com.football.dev.footballapp.services.MatchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final MatchDTOMapper mapper;

    public MatchServiceImpl(MatchRepository matchRepository, MatchDTOMapper mapper) {
        this.matchRepository = matchRepository;
        this.mapper = mapper;
    }

    @Override
    public void insertMatch(MatchDTO matchDTO) {
        matchRepository.save(new Match(matchDTO.homeTeamId(),matchDTO.awayTeamId(),matchDTO.matchDate(),matchDTO.stadium(),matchDTO.result(),matchDTO.homeTeamScore(),matchDTO.awayTeamScore(), matchDTO.round()));
    }

    @Override
    public Optional<MatchDTO> selectMatchById(Long id) {
        return matchRepository.findById(id).map(mapper);
    }

    @Override
    public List<MatchDTO> listAllMatch() {
        return matchRepository.findAll().stream().map(mapper).collect(Collectors.toList());
    }

    @Override
    public void deleteMatch(Long id) {
        Match match = matchRepository.findById(id).get();
        match.setIsDeleted(true);
        matchRepository.save(match);
    }

    @Override
    public void updateMatch(Long id, MatchDTO matchDTO) {

        matchRepository.findById(id).ifPresent(dbMatch->{
            dbMatch.setHomeTeamId(matchDTO.homeTeamId());
            dbMatch.setAwayTeamId(matchDTO.awayTeamId());
            dbMatch.setMatchDate(matchDTO.matchDate());
            dbMatch.setStadium(matchDTO.stadium());
            dbMatch.setResult(matchDTO.result());
            dbMatch.setHomeTeamScore(matchDTO.homeTeamScore());
            dbMatch.setAwayTeamScore(matchDTO.awayTeamScore());
        });

    }
}
