package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.models.Club;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class ClubToDTOMapper implements Function<Club, ClubDto> {
  @Override
  public ClubDto apply(Club club) {
    return new ClubDto(club.getId(), club.getName(), club.getFoundedYear(), club.getCity(), club.getWebsite());
  }
}
