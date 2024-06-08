package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.*;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.services.RoundService;
import com.football.dev.footballapp.services.SeasonService;
import org.springframework.data.domain.Page;
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

    @PostMapping("/{leagueId}/create")
    public ResponseEntity<Long> createSeason(@PathVariable("leagueId") Long leagueId,
                                               @RequestBody SeasonDto seasonDto) {
        Long id = seasonService.saveSeason(seasonDto,leagueId);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
    @GetMapping("/{leagueId}/")
    public ResponseEntity<Page<SeasonDto>> getSeasons(@PathVariable("leagueId") Long leagueId,
                                                                 @RequestParam(defaultValue = "0") int pageNumber,
                                                                 @RequestParam(defaultValue = "2") int pageSize) {
        Page<SeasonDto> seasonDtoPage = seasonService.retrieveSeasons(leagueId,pageNumber,pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(seasonDtoPage);
    }

    @GetMapping("/{leagueId}/{seasonId}")
    public ResponseEntity<SeasonDto> getSeason(@PathVariable("seasonId") Long seasonId,
                                               @PathVariable("leagueId") Long leagueId) {
        return ResponseEntity.status(HttpStatus.OK).body(seasonService.getSeason(seasonId,leagueId));
    }
    @PutMapping("/{leagueId}/edit/{seasonId}")
    public ResponseEntity<String> editRound(@PathVariable("leagueId") Long leagueId,
                                            @PathVariable("seasonId") Long seasonId,
                                            @RequestBody SeasonDto seasonDto) {
        seasonService.updateSeason(seasonDto,seasonId,leagueId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{leagueId}/delete/{seasonId}")
    public ResponseEntity<SeasonDto> deleteSeason(@PathVariable("leagueId") Long leagueId,
                                                @PathVariable("seasonId") Long seasonId) {
        seasonService.deleteSeason(seasonId,leagueId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping ("/{leagueId}/generate/{seasonId}")
    public ResponseEntity<String> generateRoundsAndMatches(@PathVariable("leagueId") Long leagueId,
                                                           @PathVariable("seasonId") Long seasonId) {
    seasonService.generateRoundsAndMatches(seasonId);
    return ResponseEntity.status(HttpStatus.OK).body("Rounds and Matches generated for season with ID: " + seasonId);
    }





//    @GetMapping
//    public ResponseEntity<List<SeasonDto>> listAllSeasons() {
//        List<SeasonDto> seasons = seasonService.getAllSeasons();
//        return ResponseEntity.ok(seasons);
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<SeasonDto> getSeasonById(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(seasonService.getSeasonById(id).get());
//    }
//
//    @PostMapping("/save/{leagueId}")
//    public ResponseEntity<Void> createSeason(@PathVariable("leagueId") Long leagueId, @RequestBody SeasonDto seasonDto) {
//        SeasonDto updatedSeasonDto = new SeasonDto(seasonDto.getName(), new LeagueDTO(leagueId, null, null, null, null));
//        seasonService.saveSeason(updatedSeasonDto);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Void> updateSeason(@PathVariable("id") Long id, @RequestBody SeasonDto seasonDto) {
//        seasonService.updateSeason(id, seasonDto);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteSeason(@PathVariable("id") Long id) {
//        seasonService.deleteSeason(id);
//        return ResponseEntity.ok().build();
//    }
//    @PostMapping("/{seasonId}/rounds")
//    public ResponseEntity<Void> createRoundsForSeason(@PathVariable("seasonId") Long seasonId, @RequestBody RoundDto roundDto) {
//        try {
//           seasonService.createRoundForSeason(seasonId,roundDto);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        } catch (ResourceNotFoundException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//    @GetMapping("/{seasonId}/rounds")
//    public ResponseEntity<List<RoundDto>> getRoundsWithMatchesForSeason(@PathVariable("seasonId") Long seasonId) {
//        try {
//            List<RoundDto> rounds = seasonService.getRoundsWithMatchesForSeason(seasonId);
//            return ResponseEntity.ok(rounds);
//        } catch (ResourceNotFoundException ex) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
