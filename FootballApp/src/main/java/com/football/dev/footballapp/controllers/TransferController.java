package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.TransferDTO;
import com.football.dev.footballapp.services.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transfers")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    public ResponseEntity<List<TransferDTO>> showAllTransfers(){
        return ResponseEntity.ok(transferService.listTransfers());
    }
    @PostMapping
    public void makeTransfer(@RequestBody TransferDTO transferDTO){
        transferService.makeTransfer(transferDTO);
    }
    @PutMapping("{id}")
    public void editTransfer(@PathVariable("id")Long id , @RequestBody TransferDTO transferDTO){
        transferService.editTransfer(id,transferDTO);
    }

}
