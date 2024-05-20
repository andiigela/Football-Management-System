package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.TransferDTO;
import com.football.dev.footballapp.services.impl.TransferServiceImpl;

import java.util.List;
import java.util.Optional;

public interface TransferService {
    void makeTransfer(TransferDTO transferDto);
    List<TransferDTO> listTransfers();
    void editTransfer(Long id , TransferDTO transferDTO);
}
