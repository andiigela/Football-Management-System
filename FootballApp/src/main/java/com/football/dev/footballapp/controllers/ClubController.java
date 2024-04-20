package com.football.dev.footballapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.services.ClubService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/clubs")
@CrossOrigin("http://localhost:4200")
public class ClubController {
    private final ClubService clubService;
    private final ObjectMapper objectMapper;


    public ClubController(ClubService clubService, ObjectMapper objectMapper){
        this.clubService = clubService;
        this.objectMapper = objectMapper;

    }

    @PostMapping("/create")
    public ResponseEntity<String> createClub(@RequestParam("clubDto") String clubDto) {
        try {
            ClubDto clubDtoMapped = objectMapper.readValue(clubDto,ClubDto.class);
            clubService.saveClub(clubDtoMapped);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: " + e.getMessage());
        }
    }

    @GetMapping("/")
    public List<Club> getAllClubs() {
        return clubService.getAllClubs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Club> getClub(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clubService.getClubById(id));
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editClub(@RequestParam("clubDto") String clubDto,
                                             @PathVariable("id") Long id) {
        try {
            ClubDto clubDtoMapped = objectMapper.readValue(clubDto, ClubDto.class);
            clubService.updateClub(clubDtoMapped,id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClub(@PathVariable("id") Long id){
        clubService.deleteClub(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
