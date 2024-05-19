package com.football.dev.footballapp.services;
import com.football.dev.footballapp.dto.InjuryDto;
import com.football.dev.footballapp.models.Injury;
import org.springframework.data.domain.Page;

public interface InjuryService {
    void saveInjury(InjuryDto injuryDto, Long playerId);
    Page<InjuryDto> retrieveInjuries(Long playerId,int pageNumber, int pageSize);
    InjuryDto getInjury(Long playerId,Long injuryId);
    void updateInjury(InjuryDto injuryDto, Long injuryId, Long playerId);
    void deleteInjury(Long injuryId, Long playerId);
}
