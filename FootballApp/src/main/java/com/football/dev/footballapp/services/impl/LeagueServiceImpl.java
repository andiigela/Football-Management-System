package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.mapper.LeagueDTOMapper;
import com.football.dev.footballapp.models.ES.LeagueES;
import com.football.dev.footballapp.models.League;
import com.football.dev.footballapp.models.Season;
import com.football.dev.footballapp.repository.esrepository.LeagueRepositoryES;
import com.football.dev.footballapp.repository.jparepository.LeagueRepository;
import com.football.dev.footballapp.repository.jparepository.SeasonRepository;
import com.football.dev.footballapp.services.LeagueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeagueServiceImpl implements LeagueService {
    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final LeagueRepositoryES leagueRepositoryES;
    private final LeagueDTOMapper leagueDTOMapper;

    public LeagueServiceImpl(LeagueRepository leagueRepository,
                             SeasonRepository seasonRepository,
                             LeagueRepositoryES leagueRepositoryES,
                             LeagueDTOMapper leagueDTOMapper) {
        this.leagueRepository = leagueRepository;
        this.seasonRepository = seasonRepository;
        this.leagueRepositoryES = leagueRepositoryES;
        this.leagueDTOMapper = leagueDTOMapper;
    }

    @Override
    public void insertLeague(LeagueDTO leagueDTO) {
        League league = new League(leagueDTO.getName(), leagueDTO.getStart_date(),
                leagueDTO.getEnd_date(), leagueDTO.getDescription());
        LeagueES leagueES = new LeagueES(leagueDTO.getIdEs(),leagueDTO.getName(),
                leagueDTO.getStart_date(), leagueDTO.getEnd_date(), leagueDTO.getDescription());
        leagueRepository.save(league);
        leagueRepositoryES.save(leagueES);
    }

    @Override
    public Optional<LeagueDTO> selectLeagueByName(String name) {
        return leagueRepository.findAll().stream()
                .filter(l -> l.getName().equals(name))
                .map(leagueDTOMapper::apply)
                .findAny();
    }

    @Override
    public Optional<LeagueDTO> selectLeagueById(Long id) {
        return leagueRepository.findById(id)
                .map(leagueDTOMapper::apply);
    }

    @Override
    public Page<LeagueDTO> listAllLeagues(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<League> leaguePage = leagueRepository.findAll(pageRequest);
        return leaguePage.map(leagueDTOMapper::apply);
    }

    @Override
    public void deleteLeague(Long id) {
        leagueRepository.findById(id).ifPresent(league -> {
            league.setIsDeleted(true);
            league.setName(league.getName() + " - " + league.getId());
            leagueRepository.save(league);
        });
    }

    @Override
    public void updateLeague(Long id, LeagueDTO leagueDTO) {
        leagueRepository.findById(id).ifPresent(dbLeague -> {
            dbLeague.setName(leagueDTO.getName());
            dbLeague.setStart_date(leagueDTO.getStart_date());
            dbLeague.setEnd_date(leagueDTO.getEnd_date());
            dbLeague.setDescription(leagueDTO.getDescription());
            leagueRepository.save(dbLeague);
        });
    }

    @Override
    public void createSeasonForLeague(Long leagueId, SeasonDto seasonDto) throws ResourceNotFoundException {
        League league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new ResourceNotFoundException("League not found with id: " + leagueId));
        Season season = new Season();
        season.setName(seasonDto.getName());
        season.setLeague(league);
        seasonRepository.save(season);
        league.getSeasons().add(season);
        leagueRepository.save(league);
    }

    @Override
    public List<SeasonDto> getSeasonsForLeague(Long leagueId) throws ResourceNotFoundException {
        League league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new ResourceNotFoundException("League not found with id: " + leagueId));
        List<Season> seasons = league.getSeasons();
        return seasons.stream()
                .map(season -> new SeasonDto(season.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LeagueES> searchLeaguesByName(String name) {
        return leagueRepositoryES.findByName(name);
    }


}
