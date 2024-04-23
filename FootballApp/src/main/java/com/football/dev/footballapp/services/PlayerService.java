package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Player;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface PlayerService {
    void savePlayer(PlayerDto playerDto, MultipartFile file);
    Page<Player> retrievePlayers(int pageNumber, int pageSize);
    Player getPlayer(Long id);
    void updatePlayer(PlayerDto playerDto, Long id, MultipartFile file);
    void deletePlayer(Long id);
}
