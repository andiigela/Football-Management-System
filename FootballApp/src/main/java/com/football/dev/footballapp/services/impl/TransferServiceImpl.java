package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.TransferDTO;
import com.football.dev.footballapp.mapper.TransferDTOMapper;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.models.Transfer;
import com.football.dev.footballapp.repository.jparepository.PlayerRepository;
import com.football.dev.footballapp.repository.jparepository.TransferRepository;
import com.football.dev.footballapp.services.PlayerService;
import com.football.dev.footballapp.services.TransferService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final TransferDTOMapper transferDTOMapper;

    private final PlayerService playerService;

    private final PlayerRepository playerRepository;

  public TransferServiceImpl(TransferRepository transferRepository, TransferDTOMapper transferDTOMapper, PlayerService playerService, PlayerRepository playerRepository) {
    this.transferRepository = transferRepository;
    this.transferDTOMapper = transferDTOMapper;
    this.playerService = playerService;
    this.playerRepository = playerRepository;
  }

  @Override
    public void makeTransfer(TransferDTO transferDto) {
        Player player = playerService.getPlayer(transferDto.player().getId());
        transferRepository.save(new Transfer(transferDto.player(),player.getClub(),transferDto.newClub(),transferDto.transferDate(), transferDto.transferFee()));
        player.setClub(transferDto.newClub());
        playerRepository.save(player);

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
