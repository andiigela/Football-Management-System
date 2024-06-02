package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.LeagueDTO;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.mapper.LeagueDTOMapper;
import com.football.dev.footballapp.mapper.SeasonDtoMapper;
import com.football.dev.footballapp.models.League;
import com.football.dev.footballapp.models.Season;
import com.football.dev.footballapp.repository.jparepository.SeasonRepository;
import com.football.dev.footballapp.repository.jparepository.LeagueRepository;
import com.football.dev.footballapp.services.LeagueService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class LeagueServiceImpl implements LeagueService {
    private final LeagueRepository leagueRepository;
    private final LeagueDTOMapper leagueDTOMapper;
    private final SeasonRepository seasonRepository;
    private final SeasonDtoMapper seasonDtoMapper;
    private final FileUploadServiceImpl fileUploadService;

  public LeagueServiceImpl(LeagueRepository leagueRepository, LeagueDTOMapper leagueDTOMapper, SeasonRepository seasonRepository, SeasonDtoMapper seasonDtoMapper, FileUploadServiceImpl fileUploadService) {
    this.leagueRepository = leagueRepository;
    this.leagueDTOMapper = leagueDTOMapper;
    this.seasonRepository = seasonRepository;
    this.seasonDtoMapper = seasonDtoMapper;
    this.fileUploadService = fileUploadService;
  }

  @Override
    public void insertLeague(LeagueDTO leagueDTO) {
        leagueRepository.save(new League(leagueDTO.name(),leagueDTO.founded(),leagueDTO.description(),leagueDTO.picture()));
    }

    @Override
    public Optional<LeagueDTO> selectLeagueByName(String name) {
        return leagueRepository.findAll().stream().filter(l->l.getName().equals(name)).map(leagueDTOMapper).findAny();
    }

    @Override
    public Optional<LeagueDTO> selectLeagueById(Long id) {
        return leagueRepository.findAll().stream().filter(l->l.getId().equals(id)).map(leagueDTOMapper).findAny();
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
            dbLeague.setFounded(leagueDTO.founded());
            dbLeague.setDescription(leagueDTO.description());
            dbLeague.setPicture(dbLeague.getPicture());

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


    }
    @Override
    public List<SeasonDto> getSeasonsForLeague(Long leagueId) throws ResourceNotFoundException {
        League league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new ResourceNotFoundException("League not found with id: " + leagueId));

      return null;
    }

  @Override
  public void insertLeague(MultipartFile file, LeagueDTO leagueDTO1) {
    League league = new League(leagueDTO1.name(),leagueDTO1.founded(),leagueDTO1.description(),leagueDTO1.picture());
    String picturePath = fileUploadService.uploadFile(leagueDTO1.name(), file);
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
      String fileUpload = fileUploadService.uploadFile(leagueDtoMapper.name(), file);
      if (fileUpload==null){
        throw new RuntimeException("Failed to upload file.");
      }
      league.setPicture(fileUpload);

    }
    league.setFounded(leagueDtoMapper.founded());
    league.setDescription(leagueDtoMapper.description());
    league.setName(leagueDtoMapper.name());

    leagueRepository.save(league);
  }
}
