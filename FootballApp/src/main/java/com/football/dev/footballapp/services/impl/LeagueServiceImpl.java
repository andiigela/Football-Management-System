package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.mapper.LeagueDTOMapper;
import com.football.dev.footballapp.mapper.SeasonDtoMapper;
import com.football.dev.footballapp.models.League;
import com.football.dev.footballapp.models.Season;
import com.football.dev.footballapp.repository.LeagueRepository;
import com.football.dev.footballapp.repository.SeasonRepository;
import com.football.dev.footballapp.services.LeagueService;
import com.football.dev.footballapp.services.SeasonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeagueServiceImpl implements LeagueService {
    private final LeagueRepository leagueRepository;
    private final LeagueDTOMapper leagueDTOMapper;
    private final SeasonRepository seasonRepository;
    private final SeasonDtoMapper seasonDtoMapper;

    public LeagueServiceImpl(LeagueRepository leagueRepository,
                             LeagueDTOMapper leagueDTOMapper,
                             SeasonRepository seasonRepository,
                             SeasonDtoMapper seasonDtoMapper) {
        this.leagueRepository = leagueRepository;
        this.leagueDTOMapper = leagueDTOMapper;
        this.seasonRepository = seasonRepository;
        this.seasonDtoMapper = seasonDtoMapper;
    }

    @Override
    public void insertLeague(LeagueDTO leagueDTO) {
        leagueRepository.save(new League(leagueDTO.name(),leagueDTO.start_date(),leagueDTO.end_date(),leagueDTO.description()));
    }

    @Override
    public Optional<LeagueDTO> selectLeagueByName(String name) {
        return leagueRepository.findAll().stream().filter(l->l.getName().equals(name)).map(leagueDTOMapper).findAny();
    }

    @Override
    public Optional<LeagueDTO> selectLeagueById(Long id) {
        return  leagueRepository.findAll().stream().filter(l->l.getId().equals(id)).map(leagueDTOMapper).findAny();
    }

    @Override
    public Page<LeagueDTO> listAllLeagues(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<League> leaguePage = leagueRepository.findAll(pageRequest);
        Page<LeagueDTO> leagueDtos = leaguePage.map(leagueDTOMapper);
        return leagueDtos;
    }

    @Override
    public void deleteLeague(Long id) {
        League league = leagueRepository.findById(id).get();
        league.setIsDeleted(true);
        league.setName(league.getName() + " - " + league.getId());
        leagueRepository.save(league);
    }

    @Override
    public void updateLeague(Long id, LeagueDTO leagueDTO) {

        leagueRepository.findById(id).ifPresent(dbLeague->{
            dbLeague.setName(leagueDTO.name());
            dbLeague.setStart_date(leagueDTO.start_date());
            dbLeague.setEnd_date(leagueDTO.end_date());
            dbLeague.setDescription(leagueDTO.description());

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
        return seasons.stream().map(seasonDtoMapper).collect(Collectors.toList());
    }
}
