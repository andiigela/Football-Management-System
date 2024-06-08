package com.football.dev.footballapp.mapper;
import com.football.dev.footballapp.dto.ContractDto;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Contract;
import com.football.dev.footballapp.models.Player;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
public class PlayerToDTOMapper implements Function<Player, PlayerDto> {
    private Function<Contract, ContractDto> contractDtoMapper;
    public PlayerToDTOMapper(Function<Contract, ContractDto> contractDtoMapper) {
        this.contractDtoMapper = contractDtoMapper;
    }
    @Override
    public PlayerDto apply(Player player) {
        return new PlayerDto(player.getId(),player.getName(),player.getHeight(),player.getWeight(),player.getShirtNumber(),player.getImagePath(),player.getPreferred_foot().toString(),player.getPosition().toString(),player.getContracts(),contractDtoMapper);
    }
}
