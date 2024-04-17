package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.PlayerScoutedDto;
import com.football.dev.footballapp.models.PlayerScouted;
import com.football.dev.footballapp.services.PlayerScoutedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player-scouted")
public class PlayerScoutedController {

    private final PlayerScoutedService playerScoutedService;

    public PlayerScoutedController(PlayerScoutedService playerScoutedService) {
        this.playerScoutedService = playerScoutedService;
    }

    @GetMapping("/reports")
    public ResponseEntity<List<PlayerScouted>> getAllPlayerScoutedReports() {
        List<PlayerScouted> playerScoutedReports = playerScoutedService.getAllPlayerScoutedReports();
        return ResponseEntity.ok(playerScoutedReports);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editPlayerDetails(@PathVariable("id") Long id, @RequestBody PlayerScoutedDto playerScoutedDto) {
        try {
            playerScoutedService.editPlayerDetails(playerScoutedDto, id);
            return ResponseEntity.ok("Player details updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update player details: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlayerScoutedReport(@PathVariable("id") Long id) {
        try {
            playerScoutedService.deletePlayerScoutedReport(id);
            return ResponseEntity.ok("Player scouted report deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete player scouted report: " + e.getMessage());
        }
    }
}
