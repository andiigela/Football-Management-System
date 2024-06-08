package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.ES.RoundES;
import com.football.dev.footballapp.models.Round;
import com.football.dev.footballapp.models.Season;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface RoundService {
    Page<RoundDto> retrieveRounds(Long seasonId, int pageNumber, int pageSize);
    RoundDto getRound(Long seasonId,Long roundId);
    void updateRound(RoundDto roundDto, Long roundId, Long seasonId);
    void deleteRound(Long roundId, Long seasonId);
    List<Round> generateRoundsAndSave(List<Club> clubs, int gamesPerRound, LocalDateTime startDate, Season id);
    void saveRounds(List<Round> rounds);

    Page<RoundES> findRoundsByStartDateBetween(Date startDate, Date endDate, int pageNumber, int pageSize);

}
