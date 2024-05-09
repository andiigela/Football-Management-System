package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.models.Round;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RoundDtoMapper implements Function<Round, RoundDto> {

    private final MatchDTOMapper matchDtoMapper;

    public RoundDtoMapper(MatchDTOMapper matchDtoMapper) {
        this.matchDtoMapper = matchDtoMapper;
    }

    @Override
    public RoundDto apply(Round round) {
        return new RoundDto(round.getStart_date(), round.getEnd_date(), round.getMatches());
    }
}