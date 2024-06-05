package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.*;
import com.football.dev.footballapp.services.MatchEventService;
import com.football.dev.footballapp.services.MatchService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/matches")
public class MatchController {
    private final MatchService matchService;
    private final MatchEventService matchEventService;

  public MatchController(MatchService matchService, MatchEventService matchEventService) {
    this.matchService = matchService;
    this.matchEventService = matchEventService;
  }

  @PostMapping("/{roundId}/create")
    public ResponseEntity<String> createMatch(@PathVariable("roundId") Long roundId,@RequestBody MatchDTO matchDTO) {
        matchService.saveMatch(matchDTO,roundId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{roundId}/")
    public ResponseEntity<PageResponseDto<MatchDTO>> getMatches(@PathVariable("roundId") Long roundId, @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Page<MatchDTO> matchDtoPage = matchService.retrieveMatches(roundId,page,size);
        PageResponseDto<MatchDTO> responseDto = new PageResponseDto<>(
                matchDtoPage.getContent(),
                matchDtoPage.getNumber(),
                matchDtoPage.getSize(),
                matchDtoPage.getTotalElements()
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/{roundId}/{matchId}")
    public ResponseEntity<MatchDTO> getMatch(@PathVariable("roundId") Long roundId,@PathVariable("matchId") Long matchId) {
        return ResponseEntity.status(HttpStatus.OK).body(matchService.getMatch(roundId,matchId));
    }
    @PutMapping("/{roundId}/edit/{matchId}")
    public ResponseEntity<String> editMatch(@PathVariable("roundId") Long roundId,@PathVariable("matchId") Long matchId,
                                            @RequestBody MatchDTO matchDTO) {
        matchService.updateMatch(matchDTO,matchId,roundId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/{roundId}/delete/{matchId}")
    public ResponseEntity<RoundDto> deleteMatch(@PathVariable("matchId") Long matchId,@PathVariable("roundId") Long roundId) {
        matchService.deleteMatch(matchId,roundId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{roundId}/{matchId}/matchEvents")
    public ResponseEntity<List<MatchEventDTO>>getMatchEvents(@PathVariable("roundId") Long roundId,@PathVariable("matchId") Long matchId){
      return ResponseEntity.status(HttpStatus.OK).body(matchEventService.getMatchEventsForMatch(matchId));
    }
    @PostMapping("/{roundId}/{matchId}/createMatchEvent")
    public ResponseEntity<String>createMatchEvent(@PathVariable("roundId") Long roundId,@PathVariable("matchId") Long matchId,@RequestBody MatchEventRequest matchEventRequest){
      matchEventService.insertMatchEvent(matchId,matchEventRequest);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/{roundId}/{matchId}/edit/{eventId}")
    public ResponseEntity<String> editMatchEvent(@PathVariable("roundId")Long roundId, @PathVariable("matchId")Long matchId, @PathVariable("eventId")Long eventId, @RequestBody MatchEventRequest matchEventRequest){
      matchEventService.updateMatchEvent(eventId,matchEventRequest);
      return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{roundId}/{matchId}/delete/{matchEventId}")
    public ResponseEntity<String> deleteMatchEvent(@PathVariable("roundId") Long roundId,@PathVariable("matchId") Long matchId,@PathVariable("matchEventId")Long matchEventId){
      matchEventService.deleteMatchEvent(matchId,matchEventId);
      return ResponseEntity.status(HttpStatus.OK).build();
    }





//    @GetMapping
//    public ResponseEntity<List<MatchDTO>>listAllMatches(){
//        return ResponseEntity.ok(matchService.listAllMatch());
//    }
//    @GetMapping("{id}")
//    public ResponseEntity<MatchDTO>returnMatchById(@PathVariable("id")Long id ){
//        return ResponseEntity.ok(matchService.selectMatchById(id).get());
//    }
//    @DeleteMapping("{id}")
//    public void deleteMatch(@PathVariable("id")Long id){
//        matchService.deleteMatch(id);
//    }
//    @PutMapping("{id}")
//    public void editMatch(@PathVariable("id")Long id , @RequestBody MatchDTO matchDTO){
//        matchService.updateMatch(id,matchDTO);
//    }
//    @PostMapping
//    public void createMatch(@RequestBody MatchDTO matchDTO){
//        matchService.insertMatch(matchDTO);
//    }

}
