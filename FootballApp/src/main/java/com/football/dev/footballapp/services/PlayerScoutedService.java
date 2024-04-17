package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.PlayerScoutedDto;
import com.football.dev.footballapp.models.PlayerScouted;

import java.util.List;

public interface PlayerScoutedService {
    List<PlayerScouted> getAllPlayerScoutedReports();
    void editPlayerDetails(PlayerScoutedDto playerScoutedDto, Long id);
    void deletePlayerScoutedReport(long id);
}
