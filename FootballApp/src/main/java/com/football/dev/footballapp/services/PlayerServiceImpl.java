package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.models.enums.Foot;
import com.football.dev.footballapp.repository.ClubRepository;
import com.football.dev.footballapp.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final Function<PlayerDto, Player> playerDtoToPlayer;
    public PlayerServiceImpl(PlayerRepository playerRepository,Function<PlayerDto, Player> playerDtoToPlayer,
                             ClubRepository clubRepository){
        this.playerRepository=playerRepository;
        this.playerDtoToPlayer=playerDtoToPlayer;
        this.clubRepository=clubRepository;
    }
    @Override
    public void savePlayer(PlayerDto playerDto){
        Player player = playerDtoToPlayer.apply(playerDto);
        if(player == null) return;
        Club clubDb = clubRepository.findClubByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(clubDb == null) throw new EntityNotFoundException("User is not authenticated.");
        player.setClub(clubDb);
        playerRepository.save(player);
    }
    @Override
    public Page<Player> retrievePlayers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Club clubDb = clubRepository.findClubByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(clubDb == null) throw new EntityNotFoundException("User is not authenticated.");
        Page<Player> playersPage = playerRepository.findPlayersByClub(clubDb,pageable);
        return playersPage;
    }
    @Override
    public Player getPlayer(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Player id must be a positive non-zero value");
        return playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));
    }

    @Override
    public void updatePlayer(PlayerDto playerDto,Long id) {
        if(playerDto == null) return;
        Player playerDb = playerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Player not found with id: " + id));
        playerDb.setName(playerDto.getName());
        playerDb.setHeight(playerDto.getHeight());
        playerDb.setWeight(playerDto.getWeight());
        playerDb.setShirtNumber(playerDto.getShirtNumber());
        playerDb.setPreferred_foot(Foot.valueOf(playerDto.getPreferred_foot()));
        playerRepository.save(playerDb);
    }
    @Override
    public void deletePlayer(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Player id must be a positive non-zero value");
        playerRepository.deleteById(id);

    }

}
