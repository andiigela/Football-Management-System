package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.models.Club;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ClubDtoMapper implements Function<ClubDto, Club> {

    @Override
    public Club apply(ClubDto clubDto) {
        return  new Club(clubDto.getId(), clubDto.getName(), clubDto.getFoundedYear(),
                clubDto.getCity(), clubDto.getWebsite());

    }
}
