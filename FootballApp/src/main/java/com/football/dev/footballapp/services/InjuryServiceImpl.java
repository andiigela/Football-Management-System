package com.football.dev.footballapp.services;
import com.football.dev.footballapp.repository.InjuryRepository;
import org.springframework.stereotype.Service;
@Service
public class InjuryServiceImpl implements InjuryService{
    private final InjuryRepository injuryRepository;
    public InjuryServiceImpl(InjuryRepository injuryRepository){
        this.injuryRepository=injuryRepository;
    }
}
