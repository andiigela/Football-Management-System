package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.ContractDto;
import com.football.dev.footballapp.models.Contract;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.repository.jparepository.ContractRepository;
import com.football.dev.footballapp.repository.jparepository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    private final PlayerRepository playerRepository;
    private final Function<Contract,ContractDto> contractDtoMapper;
    public ContractServiceImpl(ContractRepository contractRepository,PlayerRepository playerRepository,Function<Contract,ContractDto> contractDtoMapper){
        this.contractRepository=contractRepository;
        this.playerRepository=playerRepository;
        this.contractDtoMapper=contractDtoMapper;
    }
    @Override
    public void saveContract(ContractDto contractDto, Long playerId) {
        if(contractDto == null) throw new IllegalArgumentException("contractDto cannot be null");
        Player playerDb = playerRepository.findById(playerId).orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + playerId));
        contractRepository.save(new Contract(contractDto.getStartDate(),contractDto.getEndDate(), contractDto.getSalary(),contractDto.getContractType(),playerDb));
    }

    @Override
    public Page<ContractDto> retrieveContracts(Long playerId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Contract> contractPage = contractRepository.findContractsByPlayerId(playerId, pageRequest);
        Page<ContractDto> contractDtos = contractPage.map(contractDtoMapper);
        return contractDtos;
    }
    @Override
    public ContractDto getContract(Long playerId, Long contractId) {
        Optional<Contract> contract = contractRepository.findByIdAndPlayerId(contractId,playerId);
        if(contract.isPresent()) return contractDtoMapper.apply(contract.get());
        throw new EntityNotFoundException("Injury not found with ids: playerId: " + playerId + " contractId: " + contractId);
    }
    @Override
    public void updateContract(ContractDto contractDto, Long contractId, Long playerId) {
        contractRepository.findByIdAndPlayerId(contractId,playerId).ifPresent(contractDb -> {
            contractDb.setStartDate(contractDto.getStartDate());
            contractDb.setEndDate(contractDto.getEndDate());
            contractDb.setSalary(contractDto.getSalary());
            contractDb.setContractType(contractDto.getContractType());
            contractRepository.save(contractDb);
        });
    }
    @Override
    public void deleteContract(Long contractId, Long playerId) {
        contractRepository.findByIdAndPlayerId(contractId,playerId).ifPresent(contractDb -> {
            contractDb.setIsDeleted(true);
            contractRepository.save(contractDb);
        });
    }
}
