package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Round;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RoundDtoMapper implements Function<Round, RoundDto> {
    private Function<Match, MatchDTO> matchDtoMapper;
    public RoundDtoMapper(Function<Match, MatchDTO> matchDtoMapper) {
        this.matchDtoMapper = matchDtoMapper;
    }
    @Override
    public RoundDto apply(Round round) {
        return new RoundDto(round.getId(),round.getStart_date(), round.getEnd_date(), round.getMatches(), matchDtoMapper);
    }
}