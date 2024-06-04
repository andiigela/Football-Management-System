package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.PlayerScoutedDto;
import com.football.dev.footballapp.models.PlayerScoutingReports;
import com.football.dev.footballapp.repository.PlayerScoutingReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerScoutingReportServiceImpl implements PlayerScoutingReportService {

    private final PlayerScoutingReportRepository repository;

    @Autowired
    public PlayerScoutingReportServiceImpl(PlayerScoutingReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PlayerScoutingReports> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PlayerScoutingReports> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public PlayerScoutingReports Save(PlayerScoutingReports report) {
        return null;
    }


   
    @Override
    public PlayerScoutingReports save(PlayerScoutingReports report) {
        return repository.save(report);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public PlayerScoutedDto convertToDto(PlayerScoutingReports report) {
        // Implement conversion logic from PlayerScoutingReports to PlayerScoutedDto
        // Example:
        PlayerScoutedDto dto = new PlayerScoutedDto();
        dto.setPlayerName(report.getPlayer().getUserEntity().getFirstName());
        // Map other fields as needed
        return dto;
    }

    @Override
    public PlayerScoutingReports convertToEntity(PlayerScoutedDto dto) {
        // Implement conversion logic from PlayerScoutedDto to PlayerScoutingReports
        // Example:
        PlayerScoutingReports report = new PlayerScoutingReports();
        // Map fields from dto to report
        return report;
    }
}
