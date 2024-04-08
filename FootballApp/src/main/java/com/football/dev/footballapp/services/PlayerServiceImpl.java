package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.mapper.PlayerDtoMapper;
import com.football.dev.footballapp.mapper.UserEntityDTOMapper;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final Function<PlayerDto, Player> playerDtoToPlayer;
    public PlayerServiceImpl(PlayerRepository playerRepository,Function<PlayerDto, Player> playerDtoToPlayer){
        this.playerRepository=playerRepository;
        this.playerDtoToPlayer=playerDtoToPlayer;
    }
    @Override
    public void savePlayer(PlayerDto playerDto){
        Player player = playerDtoToPlayer.apply(playerDto);
        if(player == null) return;
        playerRepository.save(player);
    }
    @Override
    public List<Player> retrievePlayers() {
        return playerRepository.findAll();
    }
    @Override
    public Player getPlayer(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Player id must be a positive non-zero value");
        }
        return playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));
    }


}
