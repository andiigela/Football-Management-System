package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.*;
import com.football.dev.footballapp.services.MatchEventService;
import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.dto.PageResponseDto;
import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.models.ES.MatchES;
import com.football.dev.footballapp.services.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/matches")
public class MatchController {
    private final MatchService matchService;
    private final MatchEventService matchEventService;
    private static final Logger logger = LoggerFactory.getLogger(MatchController.class);

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
  @GetMapping("/filter")
  public ResponseEntity<Page<MatchES>> getMatchesByDateAndResult(
    @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date date,
    @RequestParam Integer homeTeamResult,
    @RequestParam Integer awayTeamResult,
    @RequestParam(defaultValue = "0") int pageNumber,
    @RequestParam(defaultValue = "10") int pageSize
  ) {

    logger.debug("Received request with date: {}, homeTeamResult: {}, awayTeamResult: {}, pageNumber: {}, pageSize: {}", date, homeTeamResult, awayTeamResult, pageNumber, pageSize);
    Page<MatchES> matchES = matchService.retrieveMatchesByDateAndResult(date,homeTeamResult,awayTeamResult,pageNumber, pageSize);
    logger.debug("Retrieved matches: {}", matchES.getContent());

    return ResponseEntity.status(HttpStatus.OK).body(matchES);
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
