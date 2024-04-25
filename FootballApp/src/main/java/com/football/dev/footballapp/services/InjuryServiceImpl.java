package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.InjuryDto;
import com.football.dev.footballapp.mapper.InjuryDtoMapper;
import com.football.dev.footballapp.models.Injury;
import com.football.dev.footballapp.models.League;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.repository.InjuryRepository;
import com.football.dev.footballapp.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InjuryServiceImpl implements InjuryService{
    private final InjuryRepository injuryRepository;
    private final InjuryDtoMapper injuryDtoMapper;
    private final PlayerRepository playerRepository;
    public InjuryServiceImpl(InjuryRepository injuryRepository,InjuryDtoMapper injuryDtoMapper,PlayerRepository playerRepository){
        this.injuryRepository=injuryRepository;
        this.injuryDtoMapper=injuryDtoMapper;
        this.playerRepository=playerRepository;
    }
    @Override
    public void saveInjury(InjuryDto injuryDto,Long playerId) {
        if(injuryDto == null) throw new IllegalArgumentException("injuryDto cannot be null");
        Player playerDb = playerRepository.findById(playerId).orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + playerId));;
        injuryRepository.save(new Injury(injuryDto.getInjuryType(),injuryDto.getInjuryDate(),injuryDto.getExpectedRecoveryTime(),injuryDto.getInjuryStatus(),playerDb));
    }
    @Override
    public Page<InjuryDto> retrieveInjuries(Long playerId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Injury> injuryPage = injuryRepository.findInjuriesByPlayerId(playerId, pageable); // Assuming this returns Page<Injury>
        List<InjuryDto> injuryDtos = injuryPage.getContent() // Get the content (List) from the Page
                .stream()
                .map(injuryDtoMapper)
                .collect(Collectors.toList());
        return PageableExecutionUtils.getPage(injuryDtos, injuryPage.getPageable(), injuryPage::getTotalPages); // Create Page<InjuryDto>
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
