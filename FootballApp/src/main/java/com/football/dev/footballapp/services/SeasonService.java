package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface SeasonService {
    void saveSeason(SeasonDto seasonDto);
    void updateSeason(Long id, SeasonDto seasonDto);
    Optional<SeasonDto> getSeasonById(Long id);
    List<SeasonDto> getAllSeasons();
    void deleteSeason(Long id);
    void createRoundForSeason(Long seasonId, RoundDto roundDto) throws ResourceNotFoundException;
}
