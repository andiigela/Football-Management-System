package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.InjuriesDto;
import com.football.dev.footballapp.dto.PlayerScoutedDto;
import com.football.dev.footballapp.models.Injuries;
import com.football.dev.footballapp.models.PlayerScouted;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerScoutedReportsDtoMapper {

    public static PlayerScoutingReport mapToPlayerScoutingReport(PlayerScoutingReportsDto dto) {
        PlayerScoutingReport report = new PlayerScoutingReport();
        report.setId(dto.getId());
        report.setPlayerName(dto.getPlayerName());
        report.setPosition(dto.getPosition());
        // Map other fields as needed
        return report;
    }

    public static PlayerScoutingReportsDto mapToPlayerScoutingReportsDto(PlayerScoutingReport report) {
        PlayerScoutingReportsDto dto = new PlayerScoutingReportsDto();
        dto.setId(report.getId());
        dto.setPlayerName(report.getPlayerName());
        dto.setPosition(report.getPosition());
        // Map other fields as needed
        return dto;
    }
}
