package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Round;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RoundService {
    Optional<RoundDto> getRoundById(Long id);
    Round createRound(RoundDto roundDto);
    List<MatchDTO> getMatchesByRoundId(Long roundId);

}
