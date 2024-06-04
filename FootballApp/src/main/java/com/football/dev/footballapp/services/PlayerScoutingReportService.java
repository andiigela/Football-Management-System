package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.PlayerScoutedDto;

import java.util.List;
import java.util.Optional;

import com.football.dev.footballapp.models.PlayerScoutingReports;
public interface PlayerScoutingReportService {
    List<PlayerScoutingReports> findAll();
    Optional<PlayerScoutingReports> findById(Long id);
    PlayerScoutingReports Save(PlayerScoutingReports report);

    PlayerScoutingReports save(PlayerScoutingReports report);

    void deleteById(Long id);

    // Conversion methods
    PlayerScoutingReports convertToEntity(PlayerScoutedDto dto);
    PlayerScoutedDto convertToDto(PlayerScoutingReports report);
}