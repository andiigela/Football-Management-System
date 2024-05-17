package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.dto.PageResponseDto;
import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.services.RoundService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rounds")
public class RoundController {
    private final RoundService roundService;

    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }
    @PostMapping("/{seasonId}/create")
    public ResponseEntity<String> createRound(@PathVariable("seasonId") Long seasonId,@RequestBody RoundDto roundDto) {
        roundService.saveRound(roundDto,seasonId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{seasonId}/")
    public ResponseEntity<Page<RoundDto>> getRounds(@PathVariable("seasonId") Long seasonId, @RequestParam(defaultValue = "0") int pageNumber,
                                                                 @RequestParam(defaultValue = "3") int pageSize) {
        Page<RoundDto> roundDtoPage = roundService.retrieveRounds(seasonId,pageNumber,pageSize);
//        PageResponseDto<RoundDto> responseDto = new PageResponseDto<>(
//                roundDtoPage.getContent(),
//                roundDtoPage.getNumber(),
//                roundDtoPage.getSize(),
//                roundDtoPage.getTotalElements()
//        );
        return ResponseEntity.status(HttpStatus.OK).body(roundDtoPage);
    }
    @GetMapping("/{seasonId}/{roundId}")
    public ResponseEntity<RoundDto> getRound(@PathVariable("seasonId") Long seasonId,@PathVariable("roundId") Long roundId) {
        return ResponseEntity.status(HttpStatus.OK).body(roundService.getRound(roundId,seasonId));
    }
    @PutMapping("/{seasonId}/edit/{roundId}")
    public ResponseEntity<String> editRound(@PathVariable("seasonId") Long seasonId,@PathVariable("roundId") Long roundId,
                                             @RequestBody RoundDto roundDto) {
        roundService.updateRound(roundDto,roundId,seasonId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/{seasonId}/delete/{roundId}")
    public ResponseEntity<RoundDto> deleteRound(@PathVariable("seasonId") Long seasonId,@PathVariable("roundId") Long roundId) {
        roundService.deleteRound(roundId,seasonId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<RoundDto> getRoundById(@PathVariable Long id) {
//        return roundService.getRoundById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//    @PostMapping("/create")
//    public ResponseEntity<Void> createRound(@RequestParam("seasonId") Long seasonId, @RequestBody RoundDto roundDto) {
//        try {
//            roundService.createRound(seasonId, roundDto);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @GetMapping("/{id}/matches")
//    public ResponseEntity<List<MatchDTO>> getMatchesForRound(@PathVariable Long id) {
//        List<MatchDTO> matches = roundService.getMatchesByRoundId(id);
//        return ResponseEntity.ok(matches);
//    }

}