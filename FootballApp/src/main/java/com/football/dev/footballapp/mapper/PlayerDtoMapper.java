package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Player;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class PlayerDtoMapper implements Function<PlayerDto, Player> {
    @Override
    public Player apply(PlayerDto playerDto) {
        return new Player(playerDto.getName(),playerDto.getHeight(),playerDto.getWeight(),playerDto.getShirtNumber());
    }
}
