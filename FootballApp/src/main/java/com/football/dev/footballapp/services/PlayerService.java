package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.ES.PlayerES;
import com.football.dev.footballapp.dto.PlayerIdDto;
import com.football.dev.footballapp.models.Player;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlayerService {
    void savePlayer(PlayerDto playerDto, MultipartFile file);
    Page<PlayerDto> retrievePlayers(int pageNumber, int pageSize);
    PlayerDto getPlayerDto(Long id);
    Player getPlayer(Long id);
    void updatePlayer(PlayerDto playerDto, Long id, MultipartFile file);
    void deletePlayer(Long id);
    Page<PlayerES> getAllPlayersSortedByHeight(int pageNumber, int pageSize);
    Page<PlayerES> getAllPlayersSortedByWeight(int pageNumber, int pageSize);
    Page<PlayerES> getAllPlayersSortedByWeightDesc(int pageNumber, int pageSize);
    Page<PlayerES> getAllPlayersSortedByHeightDesc(int pageNumber, int pageSize);
    void sendDeletePlayerPermission(Long id);
    List<PlayerIdDto> deletedPlayers();
    List<PlayerIdDto> getPlayerIdsWhoAskedPermissionFromCurrentUser();

}
