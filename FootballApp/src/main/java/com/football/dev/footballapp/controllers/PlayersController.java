package com.football.dev.footballapp.controllers;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
@CrossOrigin("http://localhost:4200")
public class PlayersController {
    private final PlayerService playerService;
    public PlayersController(PlayerService playerService){
        this.playerService=playerService;
    }
    @PostMapping("/create")
    public ResponseEntity<String> createPlayer(@RequestBody PlayerDto playerDto){
        playerService.savePlayer(playerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Player created successfully");
    }
    @PostMapping("/test")
    public String test(){
        return "test succesful";
    }
}
