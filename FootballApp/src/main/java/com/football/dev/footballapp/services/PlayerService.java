package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.dto.PlayerIdDto;
import com.football.dev.footballapp.models.Player;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlayerService {
    void savePlayer(PlayerDto playerDto, MultipartFile file);
    Page<PlayerDto> retrievePlayers(int pageNumber, int pageSize);

    List<Player> getAllPlayers();
    Player getPlayer(Long id);
    void updatePlayer(PlayerDto playerDto, Long id, MultipartFile file);
    void deletePlayer(Long id);
    void sendDeletePlayerPermission(Long id);
    List<PlayerIdDto> deletedPlayers();
    List<PlayerIdDto> getPlayerIdsWhoAskedPermissionFromCurrentUser();

    void changeTeam(Long id ,ClubDto clubDto);
}
