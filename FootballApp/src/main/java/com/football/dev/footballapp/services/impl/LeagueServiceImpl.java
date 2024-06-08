package com.football.dev.footballapp.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.mapper.LeagueDTOMapper;
import com.football.dev.footballapp.mapper.SeasonDtoMapper;
import com.football.dev.footballapp.models.ES.LeagueES;
import com.football.dev.footballapp.models.League;
import com.football.dev.footballapp.repository.esrepository.LeagueRepositoryES;
import com.football.dev.footballapp.repository.jparepository.LeagueRepository;
import com.football.dev.footballapp.repository.jparepository.SeasonRepository;
import com.football.dev.footballapp.services.LeagueService;
import jakarta.persistence.EntityNotFoundException;
import com.football.dev.footballapp.util.ElasticSearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class LeagueServiceImpl implements LeagueService {
    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final SeasonDtoMapper seasonDtoMapper;
    private final FileUploadServiceImpl fileUploadService;

    private final LeagueRepositoryES leagueRepositoryES;
    private final LeagueDTOMapper leagueDTOMapper;
    private final ElasticsearchClient elasticsearchClient;

    public LeagueServiceImpl(LeagueRepository leagueRepository, SeasonRepository seasonRepository, SeasonDtoMapper seasonDtoMapper, FileUploadServiceImpl fileUploadService, LeagueRepositoryES leagueRepositoryES, LeagueDTOMapper leagueDTOMapper, ElasticsearchClient elasticsearchClient) {
        this.leagueRepository = leagueRepository;
        this.seasonRepository = seasonRepository;
        this.seasonDtoMapper = seasonDtoMapper;
        this.fileUploadService = fileUploadService;
        this.leagueRepositoryES = leagueRepositoryES;
        this.leagueDTOMapper = leagueDTOMapper;
        this.elasticsearchClient = elasticsearchClient;
    }
  private static final Logger logger = LoggerFactory.getLogger(LeagueService.class);

    @Override
    public void insertLeague(LeagueDTO leagueDTO) {

        League league = leagueRepository.save(new League(leagueDTO.getName(),leagueDTO.getFounded(),leagueDTO.getDescription(),leagueDTO.getPicture()));

        // Generate a unique ID for the Elasticsearch document
        String esId = UUID.randomUUID().toString();

        LeagueES leagueES = new LeagueES();
        leagueES.setId(esId);  // Set a unique ID for each document
        leagueES.setDbId(league.getId());
        leagueES.setName(league.getName());
        leagueES.setDescription(league.getDescription());
        leagueES.setDeleted(false);
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
        League league = leagueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("League not found with id: " + id));

        // Mark the League entity as deleted
        league.setIsDeleted(true);
        league.setName(league.getName() + " - " + league.getId());
        leagueRepository.save(league);

        // Find the corresponding LeagueES document by dbId
        LeagueES leagueES = leagueRepositoryES.findByDbId(id);
        if (leagueES != null) {
            // Update the isDeleted field in the LeagueES document
            leagueES.setDeleted(true);
            leagueRepositoryES.save(leagueES);
        } else {
            // Handle case when LeagueES document is not found
            logger.error("LeagueES not found for dbId: {}", id);
        }
    }


    @Override
    public void updateLeague(Long id, LeagueDTO leagueDTO) {

        leagueRepository.findById(id).ifPresent(dbLeague->{
            dbLeague.setName(leagueDTO.getName());
            dbLeague.setFounded(leagueDTO.getFounded());
            dbLeague.setDescription(leagueDTO.getDescription());
            dbLeague.setPicture(dbLeague.getPicture());


            LeagueES existingLeagueES = leagueRepositoryES.findByDbId(id);
            if (existingLeagueES != null) {
                existingLeagueES.setName(leagueDTO.getName());
                existingLeagueES.setDescription(leagueDTO.getDescription());
                leagueRepositoryES.save(existingLeagueES);
            } else {
                LeagueES newLeagueES = new LeagueES();
                newLeagueES.setId(leagueDTO.getIdEs());
                newLeagueES.setDbId(dbLeague.getId());
                newLeagueES.setName(leagueDTO.getName());
                newLeagueES.setDescription(leagueDTO.getDescription());
                newLeagueES.setDeleted(false);
                leagueRepositoryES.save(newLeagueES);
            }
        });
    }


//    @Override
//    public void createSeasonForLeague(Long leagueId, SeasonDto seasonDto) throws ResourceNotFoundException {
//        League league = leagueRepository.findById(leagueId)
//                .orElseThrow(() -> new ResourceNotFoundException("League not found with id: " + leagueId));
//        Season season = new Season();
//        season.setName(seasonDto.getName());
//        season.setLeague(league);
//        seasonRepository.save(season);
//        league.getSeasons().add(season);
//        leagueRepository.save(league);
//    }

//    @Override
//    public List<SeasonDto> getSeasonsForLeague(Long leagueId) throws ResourceNotFoundException {
//        League league = leagueRepository.findById(leagueId)
//                .orElseThrow(() -> new ResourceNotFoundException("League not found with id: " + leagueId));
//
//      return null;
//    }

  @Override
  public void insertLeague(MultipartFile file, LeagueDTO leagueDTO1) {
    League league = new League(leagueDTO1.getName(),leagueDTO1.getFounded(),leagueDTO1.getDescription(),leagueDTO1.getPicture());
    String picturePath = fileUploadService.uploadFile(leagueDTO1.getName(), file);
    if(picturePath == null) throw new RuntimeException("Failed to upload file.");
    league.setPicture(picturePath);
    leagueRepository.save(league);
  }

  @Override
  public void updateLeague(LeagueDTO leagueDtoMapper, Long id, MultipartFile file) {
    if(leagueDtoMapper == null){
      return;
    }
    League league = leagueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("League not found with id: " + id));

    if (file != null && !file.isEmpty()){
      fileUploadService.deleteFile(league.getPicture());
      String fileUpload = fileUploadService.uploadFile(leagueDtoMapper.getName(), file);
      if (fileUpload==null){
        throw new RuntimeException("Failed to upload file.");
      }
      league.setPicture(fileUpload);

    }
    league.setFounded(leagueDtoMapper.getFounded());
    league.setDescription(leagueDtoMapper.getDescription());
    league.setName(leagueDtoMapper.getName());

    leagueRepository.save(league);
  }

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

    public Page<LeagueES> findLeaguesByNameES(String name, int pageNumber, int pageSize) {
        try {
            logger.info("Searching for leagues with name containing: {}", name);
            PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
            Page<LeagueES> leagues = leagueRepositoryES.findByNameContainingIgnoreCaseAndIsDeletedFalse(name, pageRequest);
            logger.info("Found leagues: {}", leagues);
            return leagues;
        } catch (Exception e) {
            logger.error("Error occurred while searching for leagues: ", e);
            throw e;
        }
    }
    @Override
    public void saveLeagueToES(LeagueDTO leagueDTO) throws IOException {
        LeagueES leagueES = new LeagueES();

        leagueES.setName(leagueDTO.getName());
        leagueES.setStartDate(leagueDTO.getStartDate());
        leagueES.setEndDate(leagueDTO.getEndDate());
        leagueES.setEndDate(leagueDTO.getEndDate());
        leagueES.setDescription(leagueDTO.getDescription());
        leagueRepositoryES.save(leagueES);

        Document documentAnnotation = LeagueES.class.getAnnotation(Document.class);
        if (documentAnnotation != null) {
            String indexName = documentAnnotation.indexName();
            logger.info("League saved to index: {}", indexName);
        } else {
            logger.warn("Index name not found in @Document annotation");
        }
    }



}
