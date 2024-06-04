package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.InjuriesDto;
import com.football.dev.footballapp.models.Injuries;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class InjuriesDtoMapper implements Function<InjuriesDto, Injuries> {

    @Override
    public Injuries apply(InjuriesDto injuriesDto) {
        Injuries injuries = new Injuries();
        // Map fields from InjuriesDto to Injuries entity
        injuries.setDescription(injuriesDto.getInjuryType());
        // Assuming other mappings are needed as well
        return injuries;
    }
}

