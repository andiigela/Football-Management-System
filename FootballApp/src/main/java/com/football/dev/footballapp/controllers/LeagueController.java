package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.services.LeagueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/league")
public class LeagueController {
    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    public ResponseEntity<List<LeagueDTO>> returnAllLeagues(){
        return ResponseEntity.ok(leagueService.listAllLeagues());
    }
    @PostMapping
    public void createLeague(@RequestBody LeagueDTO leagueDTO){
        leagueService.insertLeague(leagueDTO);
    }
    @GetMapping("{id}")
    public ResponseEntity<LeagueDTO> returnLeagueByID(@PathVariable("id") Long id) {
        Optional<LeagueDTO> leagueOptional = leagueService.selectLeagueById(id);
        return leagueOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public void deleteLeague(@PathVariable("id")Long id){
        leagueService.deleteLeague(id);
    }
    @PutMapping("{id}")
    public void editLeague(@PathVariable("id") Long id,@RequestBody LeagueDTO league) {
        Optional<LeagueDTO> leagueOptional = leagueService.selectLeagueById(id);
        if (leagueOptional.isPresent()) {
            leagueService.updateLeague(id,league);
        } else {
             ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{leagueId}/seasons")
    public ResponseEntity<Void> createSeasonForLeague(@PathVariable Long leagueId, @RequestBody SeasonDto seasonDto) {
        try {
            leagueService.createSeasonForLeague(leagueId, seasonDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{leagueId}/seasons")
    public ResponseEntity<List<SeasonDto>> getSeasonsForLeague(@PathVariable Long leagueId) {
        try {
            List<SeasonDto> seasons = leagueService.getSeasonsForLeague(leagueId);
            return ResponseEntity.ok(seasons);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
