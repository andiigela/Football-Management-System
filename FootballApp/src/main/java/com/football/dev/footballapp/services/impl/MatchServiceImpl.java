package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.mapper.MatchDTOMapper;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Stadium;
import com.football.dev.footballapp.repository.ClubRepository;
import com.football.dev.footballapp.repository.MatchRepository;
import com.football.dev.footballapp.repository.StadiumRepository;
import com.football.dev.footballapp.services.MatchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final ClubRepository clubRepository;
    private final StadiumRepository stadiumRepository;
    private final MatchDTOMapper mapper;

    public MatchServiceImpl(MatchRepository matchRepository, MatchDTOMapper mapper,
                            ClubRepository clubRepository, StadiumRepository stadiumRepository) {
        this.matchRepository = matchRepository;
        this.clubRepository = clubRepository;
        this.stadiumRepository= stadiumRepository;
        this.mapper = mapper;
    }

    @Override
    public void insertMatch(MatchDTO matchDTO) {
        Club homeTeam = clubRepository.findById(matchDTO.getHomeTeamId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Home team not found"));
        Club awayTeam = clubRepository.findById(matchDTO.getAwayTeamId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Away team not found"));

        Match match = new Match(homeTeam, awayTeam, matchDTO.getMatchDate(), matchDTO.getStadium(),
                matchDTO.getResult(), matchDTO.getHomeTeamScore(), matchDTO.getAwayTeamScore());
        matchRepository.save(match);
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
        matchRepository.findById(id).ifPresent(dbMatch -> {
            Club homeTeam = clubRepository.findById(matchDTO.getHomeTeamId().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Home team not found"));
            Club awayTeam = clubRepository.findById(matchDTO.getAwayTeamId().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Away team not found"));

            dbMatch.setHomeTeamId(homeTeam);
            dbMatch.setAwayTeamId(awayTeam);
            dbMatch.setMatchDate(matchDTO.getMatchDate());
            dbMatch.setStadium(matchDTO.getStadium());
            dbMatch.setResult(matchDTO.getResult());
            dbMatch.setHomeTeamScore(matchDTO.getHomeTeamScore());
            dbMatch.setAwayTeamScore(matchDTO.getAwayTeamScore());
            matchRepository.save(dbMatch);
        });
    }


}
