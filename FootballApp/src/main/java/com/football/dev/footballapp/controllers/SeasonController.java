package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.services.RoundService;
import com.football.dev.footballapp.services.SeasonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/seasons")
public class SeasonController {
    private final SeasonService seasonService;

    public SeasonController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @GetMapping
    public ResponseEntity<List<SeasonDto>> listAllSeasons() {
        List<SeasonDto> seasons = seasonService.getAllSeasons();
        return ResponseEntity.ok(seasons);
    }

    @GetMapping("{id}")
    public ResponseEntity<SeasonDto> getSeasonById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(seasonService.getSeasonById(id).get());
    }

    @PostMapping("/save/{leagueId}")
    public ResponseEntity<Void> createSeason(@PathVariable("leagueId") Long leagueId, @RequestBody SeasonDto seasonDto) {
        SeasonDto updatedSeasonDto = new SeasonDto(seasonDto.getName(), new LeagueDTO(leagueId, null, null, null, null));
        seasonService.saveSeason(updatedSeasonDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateSeason(@PathVariable("id") Long id, @RequestBody SeasonDto seasonDto) {
        seasonService.updateSeason(id, seasonDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSeason(@PathVariable("id") Long id) {
        seasonService.deleteSeason(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{seasonId}/rounds")
    public ResponseEntity<Void> createRoundsForSeason(@PathVariable("seasonId") Long seasonId, @RequestBody RoundDto roundDto) {
        try {
           seasonService.createRoundForSeason(seasonId,roundDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
