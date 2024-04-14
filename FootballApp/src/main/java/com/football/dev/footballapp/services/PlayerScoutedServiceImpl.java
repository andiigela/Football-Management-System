package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.PlayerScoutedDto;
import com.football.dev.footballapp.mapper.PlayerScoutedDtoMapper;
import com.football.dev.footballapp.models.PlayerScouted;
import com.football.dev.footballapp.repository.PlayerScoutedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerScoutedServiceImpl implements PlayerScoutedService {

    private final PlayerScoutedRepository playerScoutedRepository;
    private final PlayerScoutedDtoMapper playerScoutedDtoMapper;

    @Autowired
    public PlayerScoutedServiceImpl(PlayerScoutedRepository playerScoutedRepository, PlayerScoutedDtoMapper playerScoutedDtoMapper) {
        this.playerScoutedRepository = playerScoutedRepository;
        this.playerScoutedDtoMapper = playerScoutedDtoMapper;
    }

    @Override
    public List<PlayerScouted> getAllPlayerScoutedReports() {
        return playerScoutedRepository.findAll();

    }

    /*@Override
    @Transactional
    public PlayerScoutedDto editPlayerDetails(PlayerScoutedDto playerScoutedDto) {

    }

    @Override
    @Transactional
    public void deletePlayerScoutedReport(long id) {

    }*/
}
