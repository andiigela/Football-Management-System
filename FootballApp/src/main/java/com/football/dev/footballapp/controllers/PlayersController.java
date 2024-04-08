package com.football.dev.footballapp.controllers;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.security.JWTGenerator;
import com.football.dev.footballapp.services.PlayerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
@CrossOrigin("http://localhost:4200")
public class PlayersController {
    private final PlayerService playerService;
    private final JWTGenerator jwtGenerator;
    public PlayersController(PlayerService playerService,JWTGenerator jwtGenerator){
        this.playerService=playerService;
        this.jwtGenerator=jwtGenerator;
    }
    @PostMapping("/create")
    public ResponseEntity<String> createPlayer(@RequestBody PlayerDto playerDto, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        // Extract token (excluding 'Bearer ')
        String token = authorizationHeader.substring(7);

        // Validate token (e.g., using JWT library)
        if (!jwtGenerator.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        playerService.savePlayer(playerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Player created successfully");
    }
    @PostMapping("/test")
    public String test(){
        return "test succesful";
    }
}
