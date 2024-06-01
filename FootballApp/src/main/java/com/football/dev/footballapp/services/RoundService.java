package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.models.ES.RoundES;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Round;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RoundService {
    void saveRound(RoundDto roundDto, Long seasonId);
    Page<RoundDto> retrieveRounds(Long seasonId, int pageNumber, int pageSize);
    RoundDto getRound(Long seasonId,Long roundId);
    void updateRound(RoundDto roundDto, Long roundId, Long seasonId);
    void deleteRound(Long roundId, Long seasonId);
    Page<RoundES> findRoundsByStartDateBetween(Date startDate, Date endDate, int pageNumber, int pageSize);
}
