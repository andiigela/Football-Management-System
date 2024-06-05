package com.football.dev.footballapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.models.ES.LeagueES;
import com.football.dev.footballapp.services.LeagueService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/league")
public class LeagueController {
    private final LeagueService leagueService;
    private final ObjectMapper objectMapper;

  public LeagueController(LeagueService leagueService, ObjectMapper objectMapper) {
    this.leagueService = leagueService;
    this.objectMapper = objectMapper;
  }

  @GetMapping
    public ResponseEntity<Page<LeagueDTO>> returnAllLeagues(@RequestParam(defaultValue = "0") int pageNumber,
                                                            @RequestParam(defaultValue = "2") int pageSize){
        Page<LeagueDTO> leagueDtoPage = leagueService.listAllLeagues(pageNumber,pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(leagueDtoPage);

    }
    @PostMapping
    public ResponseEntity<LeagueDTO> createLeague(@RequestParam("file") MultipartFile file, @RequestParam("leagueDto")String leagueDTO){
      try {
        LeagueDTO leagueDTO1 = objectMapper.readValue(leagueDTO,LeagueDTO.class);
        leagueService.insertLeague(file,leagueDTO1);
        return ResponseEntity.status(HttpStatus.CREATED).build();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
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
    public void editLeague(@RequestParam(value = "file",required = false) MultipartFile file,@RequestParam("leagueDto") String leagueDto,@PathVariable("id") Long id) {

      try {
        LeagueDTO leagueDtoMapper = objectMapper.readValue(leagueDto, LeagueDTO.class);
        leagueService.updateLeague(leagueDtoMapper,id,file);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    @GetMapping("/matchAll")
    public String matchAll() throws IOException {
        SearchResponse<Map> searchResponse =  leagueService.matchAllServices();
        System.out.println(searchResponse.hits().hits().toString());
        return searchResponse.hits().hits().toString();
    }
    @GetMapping("/matchAllLeagues")
    public List<LeagueES> matchAllLeagues() throws IOException {
        SearchResponse<LeagueES> searchResponse =  leagueService.matchAllLeagueServices();
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
        SearchResponse<LeagueES> searchResponse =  leagueService.matchLeaguesWithName(fieldValue);
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<LeagueES>> listOfHits= searchResponse.hits().hits();
        List<LeagueES> listOfProducts  = new ArrayList<>();
        for(Hit<LeagueES> hit : listOfHits){
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }
    @GetMapping("/search")
    public ResponseEntity<Page<LeagueES>> searchLeagues(@RequestParam String name,
                                        @RequestParam int pageNumber,
                                        @RequestParam int pageSize) {
        Page<LeagueES> leagueES = leagueService.findLeaguesByNameES(name,pageNumber,pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(leagueES);
    }
    @PostMapping("/es")
    public ResponseEntity<Void> saveLeagueToES(@RequestBody LeagueDTO leagueDTO) {
        try {
            leagueService.saveLeagueToES(leagueDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
