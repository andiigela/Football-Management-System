package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.dto.PlayerIdDto;
import com.football.dev.footballapp.exceptions.UserNotFoundException;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Notification;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.models.enums.Foot;
import com.football.dev.footballapp.repository.jparepository.ClubRepository;
import com.football.dev.footballapp.repository.jparepository.PlayerRepository;
import com.football.dev.footballapp.repository.mongorepository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final AuthenticationHelperService authenticationHelperService;
    public PlayerServiceImpl(PlayerRepository playerRepository,Function<PlayerDto, Player> playerDtoToPlayer,
                             ClubRepository clubRepository,FileUploadService fileUploadService,
                             Function<Player, PlayerDto> playerToPlayerDto,SimpMessagingTemplate simpMessagingTemplate,
                             NotificationService notificationService,NotificationRepository notificationRepository,
                             AuthenticationHelperService authenticationHelperService){
        this.playerRepository=playerRepository;
        this.playerDtoToPlayer=playerDtoToPlayer;
        this.clubRepository=clubRepository;
        this.fileUploadService=fileUploadService;
        this.playerToPlayerDto=playerToPlayerDto;
        this.simpMessagingTemplate=simpMessagingTemplate;
        this.notificationService=notificationService;
        this.notificationRepository=notificationRepository;
        this.authenticationHelperService=authenticationHelperService;
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
        return playerRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));
    }
    @Override
    public void updatePlayer(PlayerDto playerDto, Long id, MultipartFile file) {
        if (playerDto == null) {
            return;
        }
        Player playerDb = playerRepository.findByIdAndIsDeletedFalse(id)
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
    public void sendDeletePlayerPermission(Long id) {
        Player playerDb = this.getPlayer(id);
        if(playerDb == null) throw new EntityNotFoundException("Player not found with specified id: " + id);
        String message = "Club " + playerDb.getClub().getName()
                + " needs permission to delete player: " + playerDb.getName() + " with id: " + playerDb.getId();
        notificationService.createPlayerDeletePermissionNotification(new Notification(id,message));
        simpMessagingTemplate.convertAndSend("/topic/playerDeleted",message);
        UserEntity currentLoggedInUser = this.authenticationHelperService.getUserEntityFromAuthentication();
        List<PlayerIdDto> notificationsSentFromCurrentUser = this.getPlayerIdsWhoAskedPermissionFromCurrentUser();
        this.simpMessagingTemplate.convertAndSend(("/topic/askedpermission/" + currentLoggedInUser.getId()),notificationsSentFromCurrentUser);
    }
    @Override
    public void deletePlayer(Long id) {
        Player playerDb = this.getPlayer(id);
        if(playerDb == null) throw new EntityNotFoundException("Player not found with specified id: " + id);
        playerDb.isDeleted = true;
        playerRepository.save(playerDb);
    }
    @Override
    public List<PlayerIdDto> deletedPlayers() {
        return playerRepository.findPlayersByIsDeleted(true).stream().map(player -> new PlayerIdDto(player.getId())).collect(Collectors.toList());
    }
    @Override
    public List<PlayerIdDto> getPlayerIdsWhoAskedPermissionFromCurrentUser() {
        UserEntity currentUser = authenticationHelperService.getUserEntityFromAuthentication();
        if(currentUser == null) throw new UserNotFoundException("User not found");
        return notificationRepository.findNotificationsByFromUserId(currentUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("Sent notifications not found with userId: " + currentUser.getId()))
                .stream().map(notification -> new PlayerIdDto(notification.getPlayerId())).collect(Collectors.toList());
    }

}
