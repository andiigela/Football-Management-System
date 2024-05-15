package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.ContractDto;
import com.football.dev.footballapp.dto.InjuryDto;
import com.football.dev.footballapp.mapper.ContractDtoMapper;
import com.football.dev.footballapp.models.Contract;
import com.football.dev.footballapp.models.Injury;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.repository.ContractRepository;
import com.football.dev.footballapp.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    private final PlayerRepository playerRepository;
    private final ContractDtoMapper contractDtoMapper;
    public ContractServiceImpl(ContractRepository contractRepository,PlayerRepository playerRepository,ContractDtoMapper contractDtoMapper){
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
    public Page<InjuryDto> retrieveContracts(Long playerId, int pageNumber, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<Injury> injuryPage = contractRepository.findContractsByPlayerId(playerId, pageable);
//        List<InjuryDto> injuryDtos = injuryPage.getContent()
//                .stream()
//                .map(injuryDtoMapper)
//                .collect(Collectors.toList());
//        return PageableExecutionUtils.getPage(injuryDtos, injuryPage.getPageable(), injuryPage::getTotalPages);
        return null;
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