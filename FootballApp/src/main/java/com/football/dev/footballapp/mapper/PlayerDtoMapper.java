package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Player;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Component
public class PlayerDtoMapper implements Function<PlayerDto, Player> {
    @Override
    public Player apply(PlayerDto playerDto) {
        Player player = new Player(playerDto.getName(),playerDto.getHeight(),playerDto.getWeight(),playerDto.getShirtNumber(), playerDto.getPreferred_foot(),playerDto.getPosition());
        return player;
    }
}
