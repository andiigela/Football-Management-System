package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Player;
import org.springframework.data.domain.Page;

public interface PlayerService {
    void savePlayer(PlayerDto playerDto);
    Page<Player> retrievePlayers(int pageNumber, int pageSize);
    Player getPlayer(Long id);
    void updatePlayer(PlayerDto playerDto, Long id);
    void deletePlayer(Long id);
}
