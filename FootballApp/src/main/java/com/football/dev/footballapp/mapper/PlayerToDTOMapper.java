package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.models.Round;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PlayerToDTOMapper implements Function<Player, PlayerDto> {

    @Override
    public PlayerDto apply(Player player) {
        PlayerDto playerDto = new PlayerDto(player.getName(),
                player.getHeight(),player.getWeight(),player.getShirtNumber(),
                player.getPreferred_foot().toString(),player.getPosition().toString());
        return playerDto;
    }
}
