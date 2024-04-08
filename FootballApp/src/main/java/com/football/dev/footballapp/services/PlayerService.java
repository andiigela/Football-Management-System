package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Player;

import java.util.List;

public interface PlayerService {
    void savePlayer(PlayerDto playerDto);
    List<Player> retrievePlayers();
    Player getPlayer(Long id);
}
