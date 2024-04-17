package com.football.dev.footballapp.mapper;

import com.football.dev.footballapp.dto.TransferDTO;
import com.football.dev.footballapp.models.Transfer;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class TransferDTOMapper implements Function<Transfer, TransferDTO> {
    @Override
    public TransferDTO apply(Transfer transfer) {
        return new TransferDTO(transfer.getId(), transfer.getPlayer(),transfer.getPreviousClub(),transfer.getNewClub(),transfer.getTransferDate(),transfer.getTransferFee());
    }
}
