package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.mapper.PlayerDtoMapper;
import com.football.dev.footballapp.mapper.UserEntityDTOMapper;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.repository.PlayerRepository;
import org.springframework.stereotype.Service;
@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerDtoMapper playerDtoMapper;
    public PlayerServiceImpl(PlayerRepository playerRepository,PlayerDtoMapper playerDtoMapper){
        this.playerRepository=playerRepository;
        this.playerDtoMapper=playerDtoMapper;
    }
    public void savePlayer(PlayerDto playerDto){
        Player player = playerDtoMapper.apply(playerDto);
        if(player == null) return;
        playerRepository.save(player);
    }



}
