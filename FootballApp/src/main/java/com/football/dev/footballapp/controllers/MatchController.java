package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.services.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/matches")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<MatchDTO>>listAllMatches(){
        return ResponseEntity.ok(matchService.listAllMatch());
    }
    @GetMapping("{id}")
    public ResponseEntity<MatchDTO>returnMatchById(@PathVariable("id")Long id ){
        return ResponseEntity.ok(matchService.selectMatchById(id).get());
    }
    @DeleteMapping("{id}")
    public void deleteMatch(@PathVariable("id")Long id){
        matchService.deleteMatch(id);
    }
    @PutMapping
    public void editMatch(@PathVariable("id")Long id , @RequestBody MatchDTO matchDTO){
        matchService.updateMatch(id,matchDTO);
    }
    @PostMapping
    public void createMatch(@RequestBody MatchDTO matchDTO){
        matchService.insertMatch(matchDTO);
    }

}
