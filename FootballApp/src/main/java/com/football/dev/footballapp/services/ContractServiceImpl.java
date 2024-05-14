package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.ContractDto;
import com.football.dev.footballapp.dto.InjuryDto;
import com.football.dev.footballapp.repository.ContractRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    public ContractServiceImpl(ContractRepository contractRepository){
        this.contractRepository=contractRepository;
    }
    @Override
    public void saveContract(ContractDto contractDto, Long playerId) {

    }
    @Override
    public Page<InjuryDto> retrieveContracts(Long playerId, int pageNumber, int pageSize) {
        return null;
    }
    @Override
    public ContractDto getContract(Long playerId, Long injuryId) {
        return null;
    }
    @Override
    public void updateContract(ContractDto injuryDto, Long injuryId, Long playerId) {

    }
    @Override
    public void deleteContract(Long injuryId, Long playerId) {
    }
}
