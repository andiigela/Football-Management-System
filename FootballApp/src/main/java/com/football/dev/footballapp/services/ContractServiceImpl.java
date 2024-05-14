package com.football.dev.footballapp.services;
import com.football.dev.footballapp.repository.ContractRepository;
import org.springframework.stereotype.Service;
@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    public ContractServiceImpl(ContractRepository contractRepository){
        this.contractRepository=contractRepository;
    }

}
