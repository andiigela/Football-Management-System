package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Player;

public interface PlayerService {
    void savePlayer(PlayerDto playerDto);
}
