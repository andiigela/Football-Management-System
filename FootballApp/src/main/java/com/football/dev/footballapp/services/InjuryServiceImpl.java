package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.InjuryDto;
import com.football.dev.footballapp.mapper.InjuryDtoMapper;
import com.football.dev.footballapp.models.Injury;
import com.football.dev.footballapp.models.League;
import com.football.dev.footballapp.repository.InjuryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InjuryServiceImpl implements InjuryService{
    private final InjuryRepository injuryRepository;
    private final InjuryDtoMapper injuryDtoMapper;
    public InjuryServiceImpl(InjuryRepository injuryRepository,InjuryDtoMapper injuryDtoMapper){
        this.injuryRepository=injuryRepository;
        this.injuryDtoMapper=injuryDtoMapper;
    }
    @Override
    public void saveInjury(InjuryDto injuryDto) {
        if(injuryDto == null) throw new IllegalArgumentException("injuryDto cannot be null");
        injuryRepository.save(new Injury(injuryDto.getInjuryType(),injuryDto.getInjuryDate(),injuryDto.getExpectedRecoveryTime(),injuryDto.getInjuryStatus()));
    }
    @Override
    public Page<InjuryDto> retrieveInjuries(Long playerId,int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        //return injuryRepository.findInjuriesByPlayerId(playerId,pageable);;
        return null;
    }
    @Override
    public Injury getInjury(Long id) {
        return null;
    }
    @Override
    public void updateInjury(InjuryDto injuryDto, Long id) {

    }
    @Override
    public void deleteInjury(Long id) {

    }
}
