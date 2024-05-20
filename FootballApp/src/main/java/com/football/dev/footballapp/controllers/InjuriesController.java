package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.ContractDto;
import com.football.dev.footballapp.dto.InjuryDto;
import com.football.dev.footballapp.dto.PageResponseDto;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.services.InjuryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/injuries")
@CrossOrigin("http://localhost:4200")
public class InjuriesController {
    private final InjuryService injuryService;
    public InjuriesController(InjuryService injuryService){
        this.injuryService=injuryService;
    }
    @PostMapping("/{playerId}/create")
    public ResponseEntity<String> createInjury(@PathVariable("playerId") Long playerId,@RequestBody InjuryDto injuryDto) {
        injuryService.saveInjury(injuryDto,playerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{playerId}/")
    public ResponseEntity<Page<InjuryDto>> getInjuries(@PathVariable("playerId") Long playerId,@RequestParam(defaultValue = "0") int pageNumber,
                                                              @RequestParam int pageSize) {
        Page<InjuryDto> injuryDtoPage = injuryService.retrieveInjuries(playerId,pageNumber,pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(injuryDtoPage);
    }
    @GetMapping("/{playerId}/{injuryId}")
    public ResponseEntity<InjuryDto> getInjury(@PathVariable("playerId") Long playerId,@PathVariable("injuryId") Long injuryId) {
        return ResponseEntity.status(HttpStatus.OK).body(injuryService.getInjury(injuryId,playerId));
    }
    @PutMapping("/{playerId}/edit/{injuryId}")
    public ResponseEntity<String> editInjury(@PathVariable("playerId") Long playerId,@PathVariable("injuryId") Long injuryId,
                                                @RequestBody InjuryDto injuryDto) {
        injuryService.updateInjury(injuryDto,injuryId,playerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/{playerId}/delete/{injuryId}")
    public ResponseEntity<InjuryDto> deleteInjury(@PathVariable("playerId") Long playerId,@PathVariable("injuryId") Long injuryId) {
        injuryService.deleteInjury(injuryId,playerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
