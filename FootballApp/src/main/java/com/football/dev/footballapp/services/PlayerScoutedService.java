package com.football.dev.footballapp.services;

import com.football.dev.footballapp.models.PlayerScouted;
import com.football.dev.footballapp.repository.PlayerScoutedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerScoutedService {

    @Autowired
    private PlayerScoutedRepository playerScoutedRepository;

    public List<PlayerScouted> findAll() {
        return playerScoutedRepository.findAll();
    }

    public Optional<PlayerScouted> findById(Long id) {
        return playerScoutedRepository.findById(id);
    }

    public PlayerScouted Save(PlayerScouted playerScouted) {
        return playerScoutedRepository.save(playerScouted);
    }

    public void deleteById(Long id) {
        playerScoutedRepository.deleteById(id);
    }
}
