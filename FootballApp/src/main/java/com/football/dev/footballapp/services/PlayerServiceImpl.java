package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.mapper.PlayerDtoMapper;
import com.football.dev.footballapp.mapper.UserEntityDTOMapper;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final Function<PlayerDto, Player> playerDtoToPlayer;
    public PlayerServiceImpl(PlayerRepository playerRepository,Function<PlayerDto, Player> playerDtoToPlayer){
        this.playerRepository=playerRepository;
        this.playerDtoToPlayer=playerDtoToPlayer;
    }
    public void savePlayer(PlayerDto playerDto){
        Player player = playerDtoToPlayer.apply(playerDto);
        if(player == null) return;
        playerRepository.save(player);
    }



}
