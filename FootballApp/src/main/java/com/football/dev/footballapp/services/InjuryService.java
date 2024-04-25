package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.InjuryDto;
import com.football.dev.footballapp.models.Injury;
import org.springframework.data.domain.Page;

public interface InjuryService {
    void saveInjury(InjuryDto injuryDto, Long playerId);
    Page<InjuryDto> retrieveInjuries(Long playerId,int pageNumber, int pageSize);
    Injury getInjury(Long id);
    void updateInjury(InjuryDto injuryDto, Long id);
    void deleteInjury(Long id);
}
