package com.football.dev.footballapp.services.impl;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.ES.LeagueES;
import com.football.dev.footballapp.models.ES.PlayerES;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.models.enums.Foot;
import com.football.dev.footballapp.repository.esrepository.PlayerRepositoryES;
import com.football.dev.footballapp.repository.jparepository.ClubRepository;
import com.football.dev.footballapp.repository.jparepository.PlayerRepository;
import com.football.dev.footballapp.services.FileUploadService;
import com.football.dev.footballapp.services.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerRepositoryES playerRepositoryES;
    private final ClubRepository clubRepository;
    private final FileUploadService fileUploadService;
    private final Function<PlayerDto, Player> playerDtoToPlayer;
    private final Function<Player, PlayerDto> playerToPlayerDto;


    public PlayerServiceImpl(PlayerRepository playerRepository,Function<PlayerDto, Player> playerDtoToPlayer,
                             ClubRepository clubRepository,FileUploadService fileUploadService,
                             Function<Player, PlayerDto> playerToPlayerDto,
                             PlayerRepositoryES playerRepositoryES){
        this.playerRepository=playerRepository;
        this.playerDtoToPlayer=playerDtoToPlayer;
        this.clubRepository=clubRepository;
        this.fileUploadService=fileUploadService;
        this.playerToPlayerDto=playerToPlayerDto;
        this.playerRepositoryES = playerRepositoryES;

    }
    @Override
    public void savePlayer(PlayerDto playerDto, MultipartFile file){
        Player player = playerDtoToPlayer.apply(playerDto);
        String fileUpload = fileUploadService.uploadFile(playerDto.getName(),file);
        if(fileUpload == null) throw new RuntimeException("Failed to upload file.");
        player.setImagePath(fileUpload); // saved filename
        if(player == null) return;
        Club clubDb = clubRepository.findClubByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(clubDb == null) throw new EntityNotFoundException("User is not authenticated.");
        player.setClub(clubDb);
        playerRepository.save(player);
        String esId = UUID.randomUUID().toString();
        PlayerES playerES = new PlayerES();
        playerES.setId(esId);  // Set a unique ID for each document
        playerES.setDbId(player.getId());
        playerES.setName(player.getName());
        playerES.setHeight(player.getHeight());
        playerES.setWeight(player.getWeight());
        playerES.setShirtNumber(player.getShirtNumber());
        playerES.setImagePath(player.getImagePath());
        playerES.setPosition(player.getPosition().toString());
        playerES.setPreferred_foot(player.getPreferred_foot().toString());
        playerRepositoryES.save(playerES);
    }

    @Override
    public Page<PlayerDto> retrievePlayers(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Club clubDb = clubRepository.findClubByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(clubDb == null) throw new EntityNotFoundException("User is not authenticated.");
        Page<Player> playersPage = playerRepository.findPlayersByClubAndIsDeletedFalseOrderByInsertDateTimeAsc(clubDb,pageRequest);
        Page<PlayerDto> playerDtos = playersPage.map(playerToPlayerDto);
        return playerDtos;
    }
    @Override
    public Player getPlayer(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Player id must be a positive non-zero value");
        return playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));
    }
    @Override
    public void updatePlayer(PlayerDto playerDto, Long id, MultipartFile file) {
        if (playerDto == null) {
            return;
        }
        Player playerDb = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));
        if (file != null && !file.isEmpty()) {
            // A new file is provided, handle file upload
            fileUploadService.deleteFile(playerDb.getImagePath()); // Deleting previous file
            String fileUpload = fileUploadService.uploadFile(playerDto.getName(), file);
            if (fileUpload == null) {
                throw new RuntimeException("Failed to upload file.");
            }
            playerDb.setImagePath(fileUpload); // Update imagePath with new file path
        }
        // Update other fields
        playerDb.setName(playerDto.getName());
        playerDb.setHeight(playerDto.getHeight());
        playerDb.setWeight(playerDto.getWeight());
        playerDb.setShirtNumber(playerDto.getShirtNumber());
        if (playerDto.getPreferred_foot() != null) {
            playerDb.setPreferred_foot(Foot.valueOf(playerDto.getPreferred_foot()));
        }
        playerRepository.save(playerDb);
    }

    @Override
    public void deletePlayer(Long id) {
        Player playerDb = this.getPlayer(id);
        if(playerDb == null) throw new EntityNotFoundException("Player not found with specified id: " + id);
        playerDb.isDeleted = true;
        playerRepository.save(playerDb);
        PlayerES playerES = playerRepositoryES.findByDbId(id);
        playerES.setDeleted(true);
        playerRepositoryES.save(playerES);

    }
    @Override
    public Page<PlayerES> getAllPlayersSortedByHeight(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return playerRepositoryES.findAllByDeletedFalseOrderByHeightAsc(pageRequest);
    }

    @Override
    public Page<PlayerES> getAllPlayersSortedByHeightDesc(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return playerRepositoryES.findAllByDeletedFalseOrderByHeightDesc(pageRequest);
    }

    @Override
    public Page<PlayerES> getAllPlayersSortedByWeight(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return playerRepositoryES.findAllByDeletedFalseOrderByWeightAsc(pageRequest);
    }

    @Override
    public Page<PlayerES> getAllPlayersSortedByWeightDesc(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return playerRepositoryES.findAllByDeletedFalseOrderByWeightDesc(pageRequest);
    }

}
