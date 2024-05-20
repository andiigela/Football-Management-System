package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.InjuryDto;
import com.football.dev.footballapp.models.Injury;
import org.springframework.stereotype.Service;
import java.util.function.Function;
@Service
public class InjuryDtoMapper implements Function<Injury, InjuryDto> {
    @Override
    public InjuryDto apply(Injury injury) {
        return new InjuryDto(injury.getId(), injury.getInjuryType(),injury.getInjuryDate(),injury.getExpectedRecoveryTime(),injury.getInjuryStatus());
    }
}
