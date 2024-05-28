package com.football.dev.footballapp.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
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
import com.football.dev.footballapp.util.ElasticSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class LeagueServiceImpl implements LeagueService {
    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final LeagueRepositoryES leagueRepositoryES;
    private final LeagueDTOMapper leagueDTOMapper;
    private ElasticsearchClient elasticsearchClient;
    public LeagueServiceImpl(LeagueRepository leagueRepository,
                             SeasonRepository seasonRepository,
                             LeagueRepositoryES leagueRepositoryES,
                             LeagueDTOMapper leagueDTOMapper,
                             ElasticsearchClient elasticsearchClient) {
        this.leagueRepository = leagueRepository;
        this.seasonRepository = seasonRepository;
        this.leagueRepositoryES = leagueRepositoryES;
        this.leagueDTOMapper = leagueDTOMapper;
        this.elasticsearchClient = elasticsearchClient;
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
            try {
                SearchResponse<LeagueES> searchResponse = elasticsearchClient.search(s -> s
                                .index("league")
                                .query(q -> q
                                        .match(m -> m
                                                .field("name")
                                                .query(league.getName())
                                        )
                                ),
                        LeagueES.class
                );

                List<Hit<LeagueES>> hits = searchResponse.hits().hits();
                for (Hit<LeagueES> hit : hits) {
                    if (hit.source().getName().equals(league.getName())) {
                        leagueRepositoryES.deleteById(hit.id());
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            LeagueES leagueES = new LeagueES(leagueDTO.getIdEs(), leagueDTO.getName(),
                    leagueDTO.getStart_date(), leagueDTO.getEnd_date(), leagueDTO.getDescription());
            leagueRepositoryES.save(leagueES);
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

//ES
    @Override
    public SearchResponse<Map> matchAllServices() throws IOException {
        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
        SearchResponse<Map> searchResponse = elasticsearchClient.search(s->s.query(supplier.get()),Map.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    @Override
    public SearchResponse<LeagueES> matchAllLeagueServices() throws IOException{
        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
        SearchResponse<LeagueES> searchResponse = elasticsearchClient.search(s->s.index("league").query(supplier.get()),LeagueES.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    @Override
    public SearchResponse<LeagueES> matchLeaguesWithName(String fieldValue) throws IOException{
        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithNameField(fieldValue);
        SearchResponse<LeagueES> searchResponse = elasticsearchClient.search(s->s.index("league").query(supplier.get()),LeagueES.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    @Override
    public List<LeagueES> findLeaguesByNameES(String name) {
        return leagueRepositoryES.findByNameContainingIgnoreCase(name);
    }
}
