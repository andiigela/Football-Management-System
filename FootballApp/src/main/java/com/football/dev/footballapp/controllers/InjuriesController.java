package com.football.dev.footballapp.controllers;

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
public class InjuriesController {
    private final InjuryService injuryService;
    public InjuriesController(InjuryService injuryService){
        this.injuryService=injuryService;
    }
    @PostMapping("/create")
    public ResponseEntity<String> createInjury(@RequestBody InjuryDto injuryDto) {
        injuryService.saveInjury(injuryDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/")
    public ResponseEntity<PageResponseDto<InjuryDto>> getInjuries(@PathVariable("playerId") Long playerId,@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Page<InjuryDto> injuryDtoPage = injuryService.retrieveInjuries(playerId,page,size);
        PageResponseDto<Player> responseDto = new PageResponseDto<>(
                injuryDtoPage.getContent(),
                injuryDtoPage.getNumber(),
                injuryDtoPage.getSize(),
                injuryDtoPage.getTotalElements()
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
