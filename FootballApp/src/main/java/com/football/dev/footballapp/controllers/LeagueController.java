package com.football.dev.footballapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.services.LeagueService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/league")
public class LeagueController {
    private final LeagueService leagueService;
    private final ObjectMapper objectMapper;

  public LeagueController(LeagueService leagueService, ObjectMapper objectMapper) {
    this.leagueService = leagueService;
    this.objectMapper = objectMapper;
  }

  @GetMapping
    public ResponseEntity<Page<LeagueDTO>> returnAllLeagues(@RequestParam(defaultValue = "0") int pageNumber,
                                                            @RequestParam(defaultValue = "2") int pageSize){
        Page<LeagueDTO> leagueDtoPage = leagueService.listAllLeagues(pageNumber,pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(leagueDtoPage);

    }
    @PostMapping
    public ResponseEntity<LeagueDTO> createLeague(@RequestParam("file") MultipartFile file, @RequestParam("leagueDto")String leagueDTO){
      try {
        LeagueDTO leagueDTO1 = objectMapper.readValue(leagueDTO,LeagueDTO.class);
        leagueService.insertLeague(file,leagueDTO1);
        return ResponseEntity.status(HttpStatus.CREATED).build();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
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
    public void editLeague(@RequestParam(value = "file",required = false) MultipartFile file,@RequestParam("leagueDto") String leagueDto,@PathVariable("id") Long id) {

      try {
        LeagueDTO leagueDtoMapper = objectMapper.readValue(leagueDto, LeagueDTO.class);
        leagueService.updateLeague(leagueDtoMapper,id,file);
      } catch (IOException e) {
        throw new RuntimeException(e);
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
