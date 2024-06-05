package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.dto.StandingDTO;
import com.football.dev.footballapp.services.StandingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/standing")
public class StandingController {
  private final StandingService standingService;

    public StandingController(StandingService standingService) {
        this.standingService = standingService;
    }
   @GetMapping("{id}")
   public ResponseEntity<StandingDTO> showStanding(@PathVariable("id")Long standing_id) {
    standingService.getStandingById(standing_id);
    return ResponseEntity.status(HttpStatus.OK).body(standingService.getStandingById(standing_id).get());
  }
    @GetMapping("/season/{id}")
    public ResponseEntity<List<StandingDTO>> showAllStandingsOfASeason(@PathVariable("id")Long season_id){
    standingService.getStandingById(season_id);
    return ResponseEntity.status(HttpStatus.OK).body(standingService.getStandingsForSeason(season_id));

  }
  @PostMapping("/season/{seasonid}")
  public ResponseEntity<StandingDTO> createStanding(@PathVariable("seasonid") Long seasonid, @RequestBody List<ClubDto> clubs){
      standingService.insertAllStandings(seasonid, clubs);
      return ResponseEntity.status(HttpStatus.CREATED).build();
  }


  @PutMapping("{id}")
  public ResponseEntity<StandingDTO> updateStanding(@PathVariable("id") Long id , @RequestBody StandingDTO standingDTO){
    standingService.updateStanding(id,standingDTO);
    return ResponseEntity.status(HttpStatus.OK).build();

  }


}
