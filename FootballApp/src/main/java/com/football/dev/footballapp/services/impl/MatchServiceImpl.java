package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.mapper.MatchDTOMapper;
import com.football.dev.footballapp.models.*;
import com.football.dev.footballapp.models.ES.LeagueES;
import com.football.dev.footballapp.models.ES.MatchES;
import com.football.dev.footballapp.repository.esrepository.MatchRepositoryES;
import com.football.dev.footballapp.repository.jparepository.ClubRepository;
import com.football.dev.footballapp.repository.jparepository.MatchRepository;
import com.football.dev.footballapp.repository.jparepository.RoundRepository;
import com.football.dev.footballapp.repository.jparepository.StadiumRepository;
import com.football.dev.footballapp.services.MatchService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final MatchRepositoryES matchRepositoryES;
    private final ClubRepository clubRepository;
    private final RoundRepository roundRepository;
    private final MatchDTOMapper mapper;

    public MatchServiceImpl(MatchRepository matchRepository, MatchDTOMapper mapper,
                            ClubRepository clubRepository,
                            RoundRepository roundRepository, MatchRepositoryES matchRepositoryES) {
        this.matchRepository = matchRepository;
        this.clubRepository = clubRepository;
        this.mapper = mapper;
        this.roundRepository = roundRepository;
        this.matchRepositoryES = matchRepositoryES;
    }
    @Override
    public void saveMatch(MatchDTO matchDto, Long roundId) {
        if(matchDto == null) throw new IllegalArgumentException("matchDto cannot be null");
        Round roundDb = roundRepository.findById(roundId).orElseThrow(() -> new EntityNotFoundException("Round not found with id: " + roundId));
        Club homeTeam = clubRepository.findById(matchDto.getHomeTeam().getId())
                .orElseThrow(() -> new EntityNotFoundException("Home team not found with id: " + matchDto.getHomeTeam()));
        Club awayTeam = clubRepository.findById(matchDto.getAwayTeam().getId())
                .orElseThrow(() -> new EntityNotFoundException("Away team not found with id: " + matchDto.getAwayTeam()));

        Match match = new Match(homeTeam, awayTeam, matchDto.getMatchDate(), matchDto.getResult(), matchDto.getHomeTeamScore(), matchDto.getAwayTeamScore(), roundDb);

        matchRepository.save(match);

    }

    @Override
    public Page<MatchDTO> retrieveMatches(Long roundId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Match> matchPage = matchRepository.findMatchesByRoundId(roundId, pageable);
        List<MatchDTO> roundDtos = matchPage.getContent()
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
        return PageableExecutionUtils.getPage(roundDtos, matchPage.getPageable(), matchPage::getTotalPages);
    }
    @Override
    public MatchDTO getMatch(Long roundId, Long matchId) {
        Optional<Match> match = matchRepository.findByIdAndRoundId(matchId, roundId);
        if (match.isPresent()) {
            return mapper.apply(match.get());
        } else {
            throw new EntityNotFoundException("Match not found with ids: roundId: " + roundId + " matchId: " + matchId);
        }
    }

    @Override
    public void updateMatch(MatchDTO matchDTO, Long matchId,Long roundId) {
        matchRepository.findByIdAndRoundId(matchId,roundId).ifPresent(matchDb -> {
//            matchDb.setHomeTeamId(matchDTO.getHomeTeamId());
//            matchDb.setAwayTeamId(matchDTO.getAwayTeamId());
            matchDb.setMatchDate(matchDTO.getMatchDate());
            matchDb.setResult(matchDTO.getResult());
            matchDb.setHomeTeamScore(matchDTO.getHomeTeamScore());
            matchDb.setAwayTeamScore(matchDTO.getAwayTeamScore());
            matchRepository.save(matchDb);
        });
    }
    @Override
    public void deleteMatch(Long matchId, Long roundId) {
        matchRepository.findByIdAndRoundId(matchId,roundId).ifPresent(matchDb -> {
            matchDb.setIsDeleted(true);
            matchRepository.save(matchDb);
        });
    }

    @Override
    public Page<MatchES> retrieveMatchesByDateAndResult(Date date, Integer homeTeamResult, Integer awayTeamResult, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return matchRepositoryES.findMatchesByMatchDateAndHomeTeamScoreAndAwayTeamScore(date, homeTeamResult, awayTeamResult, pageable);
    }
//    @Override
//    public void insertMatch(MatchDTO matchDTO) {
//        Club homeTeam = clubRepository.findById(matchDTO.getHomeTeamId().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Home team not found"));
//        Club awayTeam = clubRepository.findById(matchDTO.getAwayTeamId().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Away team not found"));
//
//        Match match = new Match(homeTeam, awayTeam, matchDTO.getMatchDate(), matchDTO.getStadium(),
//                matchDTO.getResult(), matchDTO.getHomeTeamScore(), matchDTO.getAwayTeamScore());
//        matchRepository.save(match);
//    }
//
//
//    @Override
//    public Optional<MatchDTO> selectMatchById(Long id) {
//        return matchRepository.findById(id).map(mapper);
//    }
//
//    @Override
//    public List<MatchDTO> listAllMatch() {
//        return matchRepository.findAll().stream().map(mapper).collect(Collectors.toList());
//    }
//
//    @Override
//    public void deleteMatch(Long id) {
//        Match match = matchRepository.findById(id).get();
//        match.setIsDeleted(true);
//        matchRepository.save(match);
//    }
//
//    @Override
//    public void updateMatch(Long id, MatchDTO matchDTO) {
//        matchRepository.findById(id).ifPresent(dbMatch -> {
//            Club homeTeam = clubRepository.findById(matchDTO.getHomeTeamId().getId())
//                    .orElseThrow(() -> new IllegalArgumentException("Home team not found"));
//            Club awayTeam = clubRepository.findById(matchDTO.getAwayTeamId().getId())
//                    .orElseThrow(() -> new IllegalArgumentException("Away team not found"));
//
//            dbMatch.setHomeTeamId(homeTeam);
//            dbMatch.setAwayTeamId(awayTeam);
//            dbMatch.setMatchDate(matchDTO.getMatchDate());
//            dbMatch.setStadium(matchDTO.getStadium());
//            dbMatch.setResult(matchDTO.getResult());
//            dbMatch.setHomeTeamScore(matchDTO.getHomeTeamScore());
//            dbMatch.setAwayTeamScore(matchDTO.getAwayTeamScore());
//            matchRepository.save(dbMatch);
//        });
//    }


}
