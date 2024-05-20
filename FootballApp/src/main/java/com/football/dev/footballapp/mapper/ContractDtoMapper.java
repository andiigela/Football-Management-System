package com.football.dev.footballapp.mapper;
import com.football.dev.footballapp.dto.ContractDto;
import com.football.dev.footballapp.models.Contract;
import org.springframework.stereotype.Service;
import java.util.function.Function;
@Service
public class ContractDtoMapper implements Function<Contract,ContractDto> {
    @Override
    public ContractDto apply(Contract contract) {
        return new ContractDto(contract.getId(),contract.getStartDate(),contract.getEndDate(),contract.getSalary(),contract.getContractType());
    }
}
