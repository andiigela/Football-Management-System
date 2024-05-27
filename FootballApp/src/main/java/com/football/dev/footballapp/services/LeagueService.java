package com.football.dev.footballapp.services;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.models.ES.LeagueES;
import com.football.dev.footballapp.models.League;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LeagueService {
    void insertLeague(LeagueDTO leagueDTO);
    Optional<LeagueDTO> selectLeagueByName(String name);
    Optional<LeagueDTO> selectLeagueById(Long id);
    Page<LeagueDTO> listAllLeagues(int pageNumber, int pageSize);
    void deleteLeague(Long id);
    void updateLeague(Long id , LeagueDTO leagueDTO);
    void createSeasonForLeague(Long leagueId, SeasonDto seasonDto);
    List<SeasonDto> getSeasonsForLeague(Long leagueId) throws ResourceNotFoundException;
    SearchResponse<Map> matchAllServices() throws IOException;
    SearchResponse<LeagueES> matchAllLeagueServices() throws IOException;
    SearchResponse<LeagueES> matchLeaguesWithName(String fieldValue) throws IOException;
}
