package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.PlayerScoutedDto;
import com.football.dev.footballapp.mapper.InjuriesDtoMapper;
import com.football.dev.footballapp.mapper.PlayerScoutedDtoMapper;
import com.football.dev.footballapp.models.Injuries;
import com.football.dev.footballapp.models.PlayerScouted;
import com.football.dev.footballapp.repository.PlayerScoutedRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerScoutedServiceImpl implements PlayerScoutedService {

    private final PlayerScoutedRepository playerScoutedRepository;
    private final PlayerScoutedDtoMapper playerScoutedDtoMapper;
    private final InjuriesDtoMapper injuriesDtoMapper;

    @Autowired
    public PlayerScoutedServiceImpl(PlayerScoutedRepository playerScoutedRepository, PlayerScoutedDtoMapper playerScoutedDtoMapper, InjuriesDtoMapper injuriesDtoMapper) {
        this.playerScoutedRepository = playerScoutedRepository;
        this.playerScoutedDtoMapper = playerScoutedDtoMapper;
        this.injuriesDtoMapper = injuriesDtoMapper;
    }

    @Override
    public List<PlayerScouted> getAllPlayerScoutedReports() {
        return playerScoutedRepository.findAll();

    }

    @Override
    public void editPlayerDetails(PlayerScoutedDto playerScoutedDto, Long id) {
        if(playerScoutedDto == null) return;
        PlayerScouted playerScoutedDb = playerScoutedRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Player Scouted not found with id: " + id));
        playerScoutedDb.setPlayerName(playerScoutedDto.getPlayerName());
        playerScoutedDb.setPlayerAge(playerScoutedDto.getPlayerAge());
        playerScoutedDb.setPlayerSurname(playerScoutedDto.getPlayerSurname());
        playerScoutedDb.setPlayerPosition(playerScoutedDto.getPlayerPosition());
        playerScoutedDb.setPlayerHeight(playerScoutedDto.getPlayerHeight());
        playerScoutedDb.setPlayerWeight(playerScoutedDto.getPlayerWeight());
        List<Injuries> injuries = playerScoutedDto.getInjuries().stream()
                .map(injuriesDto -> injuriesDtoMapper.apply(injuriesDto))
                .collect(Collectors.toList());
        playerScoutedDb.setInjuries(injuries);
        playerScoutedRepository.save(playerScoutedDb);
    }

    @Override
    public void deletePlayerScoutedReport(long id) {
        playerScoutedRepository.deleteById(id);
    }
}
