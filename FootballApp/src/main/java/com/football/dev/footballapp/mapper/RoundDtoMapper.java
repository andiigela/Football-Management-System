package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.models.Round;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RoundDtoMapper implements Function<Round, RoundDto> {

    @Override
    public RoundDto apply(Round round) {
        return new RoundDto(round.getId(),round.getStart_date(), round.getEnd_date(), round.getMatches());
    }
}