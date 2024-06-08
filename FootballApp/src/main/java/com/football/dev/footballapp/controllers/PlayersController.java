package com.football.dev.footballapp.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.dev.footballapp.dto.PageResponseDto;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.models.ES.PlayerES;
import com.football.dev.footballapp.dto.PlayerIdDto;
import com.football.dev.footballapp.dto.PlayerTransferDTO;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.services.FileUploadService;
import com.football.dev.footballapp.services.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.util.Collection;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@CrossOrigin("http://localhost:4200")
@Slf4j
public class PlayersController {
    private final PlayerService playerService;
    private final ObjectMapper objectMapper;

    public PlayersController(PlayerService playerService,ObjectMapper objectMapper) {
        this.playerService = playerService;
        this.objectMapper=objectMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<PlayerDto> createPlayer(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("") String playerDto) {
        try {
            PlayerDto playerDtoMapped = objectMapper.readValue(playerDto,PlayerDto.class);
            playerService.savePlayer(playerDtoMapped,file);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/")
    public ResponseEntity<PageResponseDto<PlayerDto>> getPlayers(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Page<PlayerDto> playersDtoPage = playerService.retrievePlayers(page,size);
        PageResponseDto<PlayerDto> responseDto = new PageResponseDto<>(
                playersDtoPage.getContent(),
                playersDtoPage.getNumber(),
                playersDtoPage.getSize(),
                playersDtoPage.getTotalElements()
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

   @GetMapping("")
  public List<PlayerTransferDTO> getPlayers() {
    return playerService.getAllPlayers();
  }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayerDto(id));
    }
    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editPlayer(@RequestParam(value = "file",required = false) MultipartFile file,@RequestParam("playerDto") String playerDto,
                                             @PathVariable("id") Long id) {
        try {
            PlayerDto playerDtoMapped = objectMapper.readValue(playerDto, PlayerDto.class);
            playerService.updatePlayer(playerDtoMapped,id,file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/delete/permission/{id}")
    public ResponseEntity<String> sendPlayerDeletePermission(@PathVariable("id") Long id){
        playerService.sendDeletePlayerPermission(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable("id") Long id){
        playerService.deletePlayer(id);
        log.info("Player has been deleted: " + id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/sortedByHeight")
    public ResponseEntity<Page<PlayerES>> getPlayersSortedByHeight(@RequestParam(defaultValue = "0") int pageNumber,
                                                                   @RequestParam(defaultValue = "10") int pageSize) {
        Page<PlayerES> players = playerService.getAllPlayersSortedByHeight(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(players);
    }

    @GetMapping("/sortedByWeight")
    public ResponseEntity<Page<PlayerES>> getPlayersSortedByWeight(@RequestParam(defaultValue = "0") int pageNumber,
                                                                   @RequestParam(defaultValue = "10") int pageSize) {
        Page<PlayerES> players = playerService.getAllPlayersSortedByWeight(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(players);
    }

    @GetMapping("/sortedByHeightDesc")
    public ResponseEntity<Page<PlayerES>> getPlayersSortedByHeightDesc(@RequestParam(defaultValue = "0") int pageNumber,
                                                                       @RequestParam(defaultValue = "10") int pageSize) {
        Page<PlayerES> players = playerService.getAllPlayersSortedByHeightDesc(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(players);
    }

    @GetMapping("/sortedByWeightDesc")
    public ResponseEntity<Page<PlayerES>> getPlayersSortedByWeightDesc(@RequestParam(defaultValue = "0") int pageNumber,
                                                                       @RequestParam(defaultValue = "10") int pageSize) {
        Page<PlayerES> players = playerService.getAllPlayersSortedByWeightDesc(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(players);
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<PlayerIdDto>> deletedPlayers(){
        return ResponseEntity.status(HttpStatus.OK).body(playerService.deletedPlayers());
    }
    @GetMapping("/askedpermission/currentuser") // Current user player ids that sent notifications
    public ResponseEntity<List<PlayerIdDto>> getPlayerIdsOfCurrentUserAskedPermission(){
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayerIdsWhoAskedPermissionFromCurrentUser());
    }
    @GetMapping("/club/{club_id}")
    public ResponseEntity<List<PlayerDto>> getPlayersByTeam(@PathVariable Long club_id){
      return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayersOfATeam(club_id));
    }
}
