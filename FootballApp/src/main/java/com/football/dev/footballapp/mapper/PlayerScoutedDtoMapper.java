package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.InjuriesDto;
import com.football.dev.footballapp.dto.PlayerScoutedDto;
import com.football.dev.footballapp.models.Injuries;
import com.football.dev.footballapp.models.PlayerScouted;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PlayerScoutedDtoMapper implements Function<PlayerScoutedDto, PlayerScouted> {

    private final InjuriesDtoMapper injuriesDtoMapper;

    public PlayerScoutedDtoMapper(InjuriesDtoMapper injuriesDtoMapper) {
        this.injuriesDtoMapper = injuriesDtoMapper;
    }

    @Override
    public PlayerScouted apply(PlayerScoutedDto playerScoutedDto) {
        List<Injuries> injuries = mapInjuriesDtoToInjuries(playerScoutedDto.getInjuries());
        return new PlayerScouted(
                playerScoutedDto.getPlayerName(),
                playerScoutedDto.getPlayerSurname(),
                playerScoutedDto.getPlayerAge(),
                playerScoutedDto.getPlayerWeight(),
                playerScoutedDto.getPlayerHeight(),
                playerScoutedDto.getPlayerPosition(),
                injuries,
                playerScoutedDto.getReport()
        );
    }

    private List<Injuries> mapInjuriesDtoToInjuries(List<InjuriesDto> injuriesDtos) {
        return injuriesDtos.stream()
                .map(injuriesDtoMapper)
                .collect(Collectors.toList());
    }
}
