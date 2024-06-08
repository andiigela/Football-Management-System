package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.dto.TransferDTO;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.services.PlayerService;
import com.football.dev.footballapp.services.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {
    private final TransferService transferService;
    private final PlayerService playerService;
    private final Function<Club, ClubDto> clubMapper;

  public TransferController(TransferService transferService, PlayerService playerService, Function<Club, ClubDto> clubMapper) {
    this.transferService = transferService;
    this.playerService = playerService;
    this.clubMapper = clubMapper;
  }

  @GetMapping("")
    public ResponseEntity<List<TransferDTO>> showAllTransfers(){
        return ResponseEntity.ok(transferService.listTransfers());
    }
    @PostMapping("")
    public void makeTransfer(@RequestBody TransferDTO transferDTO){
      System.out.println(transferDTO);
        transferService.makeTransfer(transferDTO);
      playerService.changeTeam(transferDTO.player().id,clubMapper.apply(transferDTO.newClub()));
    }

}
