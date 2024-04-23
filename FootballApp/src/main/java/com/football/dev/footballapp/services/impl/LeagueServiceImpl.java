package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.mapper.LeagueDTOMapper;
import com.football.dev.footballapp.models.League;
import com.football.dev.footballapp.repository.LeagueRepository;
import com.football.dev.footballapp.services.LeagueService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeagueServiceImpl implements LeagueService {
    private final LeagueRepository leagueRepository;
    private final LeagueDTOMapper leagueDTOMapper;

    public LeagueServiceImpl(LeagueRepository leagueRepository, LeagueDTOMapper leagueDTOMapper) {
        this.leagueRepository = leagueRepository;
        this.leagueDTOMapper = leagueDTOMapper;
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
    public List<LeagueDTO> listAllLeagues() {
        return leagueRepository.findAll().stream().map(leagueDTOMapper).collect(Collectors.toList());
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
}
