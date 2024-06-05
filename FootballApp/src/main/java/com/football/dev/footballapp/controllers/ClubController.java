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

    @GetMapping("")
    public List<ClubDto> getAllClubs() {
        return clubService.getAllClubs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Club> getClub(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clubService.getClubById(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editClub(@RequestBody ClubDto clubDto,
                                           @PathVariable("id") Long id) {
        try {
            clubService.updateClub(clubDto, id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: " + e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClub(@PathVariable("id") Long id){
        clubService.deleteClub(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/{userId}/club")
    public ResponseEntity<?> getClubByUserId(@PathVariable Long userId) {
        try {
            Club club = clubService.getClubByUserId(userId);
            return new ResponseEntity<>(club, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch club for the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @GetMapping("/clubId/{userId}")
//    public ResponseEntity<?> getClubIdByUserId(@PathVariable Long userId) {
//        try {
//            Long clubId = clubService.getClubIdByUserId(userId);
//            return new ResponseEntity<>(clubId, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to fetch club ID for the user", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
