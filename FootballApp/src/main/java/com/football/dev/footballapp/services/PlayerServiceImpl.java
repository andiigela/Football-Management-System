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
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final FileUploadService fileUploadService;
    private final Function<PlayerDto, Player> playerDtoToPlayer;
    private final Function<Player, PlayerDto> playerToPlayerDto;
    public PlayerServiceImpl(PlayerRepository playerRepository,Function<PlayerDto, Player> playerDtoToPlayer,
                             ClubRepository clubRepository,FileUploadService fileUploadService,
                             Function<Player, PlayerDto> playerToPlayerDto){
        this.playerRepository=playerRepository;
        this.playerDtoToPlayer=playerDtoToPlayer;
        this.clubRepository=clubRepository;
        this.fileUploadService=fileUploadService;
        this.playerToPlayerDto=playerToPlayerDto;
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
    }
    @Override
    public Page<PlayerDto> retrievePlayers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Club clubDb = clubRepository.findClubByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(clubDb == null) throw new EntityNotFoundException("User is not authenticated.");
        Page<Player> playersPage = playerRepository.findPlayersByClubAndIsDeletedFalseOrderByInsertDateTimeAsc(clubDb,pageable);
        List<PlayerDto> playersDtos = playerRepository.findPlayersByClubAndIsDeletedFalseOrderByInsertDateTimeAsc(clubDb,pageable)
                .stream().map(playerToPlayerDto).collect(Collectors.toList());
        return PageableExecutionUtils.getPage(playersDtos, playersPage.getPageable(), playersPage::getTotalPages);
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
    }

}
