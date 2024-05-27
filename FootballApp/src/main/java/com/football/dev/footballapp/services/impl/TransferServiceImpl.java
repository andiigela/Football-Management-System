package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.TransferDTO;
import com.football.dev.footballapp.mapper.TransferDTOMapper;
import com.football.dev.footballapp.models.Transfer;
import com.football.dev.footballapp.repository.jparepository.TransferRepository;
import com.football.dev.footballapp.services.TransferService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final TransferDTOMapper transferDTOMapper;

    public TransferServiceImpl(TransferRepository transferRepository, TransferDTOMapper transferDTOMapper) {
        this.transferRepository = transferRepository;
        this.transferDTOMapper = transferDTOMapper;
    }

    @Override
    public void makeTransfer(TransferDTO transferDto) {
        transferRepository.save(new Transfer(transferDto.player(),transferDto.previousClub(),transferDto.newClub(),transferDto.transferDate(), transferDto.transferFee()));
    }

    @Override
    public List<TransferDTO> listTransfers() {
        return transferRepository.findAll().stream().map(transferDTOMapper).collect(Collectors.toList());
    }


    @Override
    public void editTransfer(Long id, TransferDTO transferDTO) {
        transferRepository.findById(id).ifPresent(dbTransfer->{
            dbTransfer.setPlayer(transferDTO.player());
            dbTransfer.setPreviousClub(transferDTO.previousClub());
            dbTransfer.setNewClub(transferDTO.newClub());
            dbTransfer.setTransferDate(transferDTO.transferDate());
            dbTransfer.setTransferFee(transferDTO.transferFee());
            transferRepository.save(dbTransfer);
        });
    }
}
