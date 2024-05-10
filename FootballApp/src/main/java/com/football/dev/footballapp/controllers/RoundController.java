package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.services.RoundService;
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

    @GetMapping("/{id}")
    public ResponseEntity<RoundDto> getRoundById(@PathVariable Long id) {
        return roundService.getRoundById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
//    @PostMapping("/create")
//    public ResponseEntity<Void> createRound(@RequestParam("seasonId") Long seasonId, @RequestBody RoundDto roundDto) {
//        try {
//            roundService.createRound(seasonId, roundDto);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


}