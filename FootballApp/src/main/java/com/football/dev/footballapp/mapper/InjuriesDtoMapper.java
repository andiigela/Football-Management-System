package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.InjuriesDto;
import com.football.dev.footballapp.dto.PlayerScoutedDto;
import com.football.dev.footballapp.models.Injuries;
import com.football.dev.footballapp.models.PlayerScouted;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class InjuriesDtoMapper implements Function<InjuriesDto, Injuries> {

    @Override
    public Injuries apply(InjuriesDto injuriesDto) {
        Injuries injuries = new Injuries(injuriesDto.getInjuryType(), injuriesDto.getInjuryStatus());
        return injuries;
    }
}
