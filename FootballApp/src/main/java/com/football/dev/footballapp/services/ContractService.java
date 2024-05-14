package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.ContractDto;
import com.football.dev.footballapp.dto.InjuryDto;
import org.springframework.data.domain.Page;

public interface ContractService {
    void saveContract(ContractDto contractDto, Long playerId);
    Page<InjuryDto> retrieveContracts(Long playerId, int pageNumber, int pageSize);
    ContractDto getContract(Long playerId,Long contractId);
    void updateContract(ContractDto contractDto, Long contractId, Long playerId);
    void deleteContract(Long contractId, Long playerId);
}
