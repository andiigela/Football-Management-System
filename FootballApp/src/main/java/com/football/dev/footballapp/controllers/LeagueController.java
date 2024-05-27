package com.football.dev.footballapp.controllers;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.models.ES.LeagueES;
import com.football.dev.footballapp.services.ElasticSearchService;
import com.football.dev.footballapp.services.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/league")
public class LeagueController {
    private final LeagueService leagueService;

    @Autowired
    private ElasticSearchService elasticSearchService;
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    public ResponseEntity<Page<LeagueDTO>> returnAllLeagues(@RequestParam(defaultValue = "0") int pageNumber,
                                                            @RequestParam(defaultValue = "2") int pageSize){
        Page<LeagueDTO> leagueDtoPage = leagueService.listAllLeagues(pageNumber,pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(leagueDtoPage);

    }
    @PostMapping
    public void createLeague(@RequestBody LeagueDTO leagueDTO){
        leagueService.insertLeague(leagueDTO);
    }
    @GetMapping("{id}")
    public ResponseEntity<LeagueDTO> returnLeagueByID(@PathVariable("id") Long id) {
        Optional<LeagueDTO> leagueOptional = leagueService.selectLeagueById(id);
        return leagueOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public void deleteLeague(@PathVariable("id")Long id){
        leagueService.deleteLeague(id);
    }
    @PutMapping("{id}")
    public void editLeague(@PathVariable("id") Long id,@RequestBody LeagueDTO league) {
        Optional<LeagueDTO> leagueOptional = leagueService.selectLeagueById(id);
        if (leagueOptional.isPresent()) {
            leagueService.updateLeague(id,league);
        } else {
             ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{leagueId}/seasons")
    public ResponseEntity<Void> createSeasonForLeague(@PathVariable Long leagueId, @RequestBody SeasonDto seasonDto) {
        try {
            leagueService.createSeasonForLeague(leagueId, seasonDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{leagueId}/seasons")
    public ResponseEntity<List<SeasonDto>> getSeasonsForLeague(@PathVariable Long leagueId) {
        try {
            List<SeasonDto> seasons = leagueService.getSeasonsForLeague(leagueId);
            return ResponseEntity.ok(seasons);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<LeagueES>> searchLeaguesByName(@RequestParam String name) {
        List<LeagueES> leagues = leagueService.searchLeaguesByName(name);
        return new ResponseEntity<>(leagues, HttpStatus.OK);
    }
    @GetMapping("/matchAll")
    public String matchAll() throws IOException {
        SearchResponse<Map> searchResponse =  elasticSearchService.matchAllServices();
        System.out.println(searchResponse.hits().hits().toString());
        return searchResponse.hits().hits().toString();
    }
    @GetMapping("/matchAllLeagues")
    public List<LeagueES> matchAllProducts() throws IOException {
        SearchResponse<LeagueES> searchResponse =  elasticSearchService.matchAllLeagueServices();
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<LeagueES>> listOfHits= searchResponse.hits().hits();
        List<LeagueES> listOfProducts  = new ArrayList<>();
        for(Hit<LeagueES> hit : listOfHits){
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }

    @GetMapping("/matchAllLeagues/{fieldValue}")
    public List<LeagueES> matchAllLeaguesWithName(@PathVariable String fieldValue) throws IOException {
        SearchResponse<LeagueES> searchResponse =  elasticSearchService.matchProductsWithName(fieldValue);
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<LeagueES>> listOfHits= searchResponse.hits().hits();
        List<LeagueES> listOfProducts  = new ArrayList<>();
        for(Hit<LeagueES> hit : listOfHits){
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }
}