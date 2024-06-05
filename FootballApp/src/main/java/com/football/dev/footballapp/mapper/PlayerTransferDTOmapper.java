package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.dto.PlayerTransferDTO;
import com.football.dev.footballapp.models.Player;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class PlayerTransferDTOmapper implements Function<Player, PlayerTransferDTO> {

  private final ClubToDTOMapper club;

  public PlayerTransferDTOmapper(ClubToDTOMapper club) {
    this.club = club;
  }

  @Override
  public PlayerTransferDTO apply(Player player) {
    return new PlayerTransferDTO(player.getId(), player.getName(),club.apply(player.getClub()));
  }
}
