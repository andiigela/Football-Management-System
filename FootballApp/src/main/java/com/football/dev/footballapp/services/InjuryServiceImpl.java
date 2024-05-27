package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.InjuryDto;
import com.football.dev.footballapp.mapper.InjuryDtoMapper;
import com.football.dev.footballapp.models.Injury;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.repository.jparepository.InjuryRepository;
import com.football.dev.footballapp.repository.jparepository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Player playerDb = playerRepository.findById(playerId).orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + playerId));
        injuryRepository.save(new Injury(injuryDto.getInjuryType(),injuryDto.getInjuryDate(),injuryDto.getExpectedRecoveryTime(),injuryDto.getInjuryStatus(),playerDb));
    }
    @Override
    public Page<InjuryDto> retrieveInjuries(Long playerId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Injury> injuryPage = injuryRepository.findInjuriesByPlayerId(playerId, pageRequest);
        Page<InjuryDto> injuryDtos = injuryPage.map(injuryDtoMapper);
        return injuryDtos;
    }
    @Override
    public InjuryDto getInjury(Long injuryId,Long playerId) {
        Optional<Injury> injury = injuryRepository.findByIdAndPlayerId(injuryId,playerId);
        if(injury.isPresent()) return injuryDtoMapper.apply(injury.get());
        throw new EntityNotFoundException("Injury not found with ids: playerId: " + playerId + " injuryId: " + injuryId);
    }
    @Override
    public void updateInjury(InjuryDto injuryDto, Long injuryId, Long playerId) {
        injuryRepository.findByIdAndPlayerId(injuryId,playerId).ifPresent(injuryDb -> {
            injuryDb.setInjuryType(injuryDto.getInjuryType());
            injuryDb.setInjuryDate(injuryDto.getInjuryDate());
            injuryDb.setExpectedRecoveryTime(injuryDto.getExpectedRecoveryTime());
            injuryDb.setInjuryStatus(injuryDto.getInjuryStatus());
            injuryRepository.save(injuryDb);
        });
    }
    @Override
    public void deleteInjury(Long injuryId, Long playerId) {
        injuryRepository.findByIdAndPlayerId(injuryId,playerId).ifPresent(injuryDb -> {
            injuryDb.setIsDeleted(true);
            injuryRepository.save(injuryDb);
        });
    }
}
