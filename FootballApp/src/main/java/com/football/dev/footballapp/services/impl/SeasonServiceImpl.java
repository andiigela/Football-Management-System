package com.football.dev.footballapp.services.impl;
import com.football.dev.footballapp.dto.*;
import com.football.dev.footballapp.mapper.SeasonDtoMapper;
import com.football.dev.footballapp.models.*;
import com.football.dev.footballapp.repository.jparepository.SeasonRepository;
import com.football.dev.footballapp.repository.jparepository.LeagueRepository;
import com.football.dev.footballapp.services.MatchService;
import com.football.dev.footballapp.services.RoundService;
import com.football.dev.footballapp.services.SeasonService;
import com.football.dev.footballapp.services.StandingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class  SeasonServiceImpl implements SeasonService {
    private final SeasonRepository seasonRepository;
    private final LeagueRepository leagueRepository;
    private final SeasonDtoMapper seasonDtoMapper;
    private final RoundService roundService;
    private final MatchService matchService;
    private final StandingService standingService;

  public SeasonServiceImpl(SeasonRepository seasonRepository, LeagueRepository leagueRepository, SeasonDtoMapper seasonDtoMapper, RoundService roundService, MatchService matchService, StandingService standingService) {
    this.seasonRepository = seasonRepository;
    this.leagueRepository = leagueRepository;
    this.seasonDtoMapper = seasonDtoMapper;
    this.roundService = roundService;
    this.matchService = matchService;
    this.standingService = standingService;
  }

  @Override
    public Long  saveSeason(SeasonDto seasonDto,Long leagueId) {
        if(seasonDto == null) throw new IllegalArgumentException("seasonDto cannot be null");
        League leagueDb = leagueRepository.findById(leagueId).orElseThrow(() -> new EntityNotFoundException("League not found with id: " + leagueId));
        Season season = new Season(seasonDto.getName(),seasonDto.getStart_date(),seasonDto.getEnd_date(),seasonDto.getHeadToHead().longValue(),seasonDto.getNumberOfStandings().longValue(), leagueDb);
        season =seasonRepository.save(season);


        return season.id;
    }
    @Override
    public void generateRoundsAndMatches(Long id){
      Season season = seasonRepository.findById(id).get();
      SeasonDto seasonDto = seasonDtoMapper.apply(season);
      int gamesPerRound=seasonDto.getNumberOfStandings()/2;
      List<Club> clubs=standingService.getAllClubsWhereSeasonId(seasonDto.getId());
      List<Round> rounds = roundService.generateRoundsAndSave(clubs,gamesPerRound,seasonDto.getStart_date(),season) ;




      roundService.saveRounds(rounds);

    }
    @Override
    public Page<SeasonDto> retrieveSeasons(Long leagueId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Season> seasonPage = seasonRepository.findSeasonsByLeagueIdAndIsDeletedFalseOrderByInsertDateTimeDesc(leagueId, pageRequest);
        Page<SeasonDto> seasonDtos = seasonPage.map(seasonDtoMapper);
//        List<SeasonDto> seasonDtos = seasonPage.getContent()
//                .stream()
//                  .filter(season -> season.isDeleted = )
//                .map(seasonDtoMapper)
//                .collect(Collectors.toList());
        return seasonDtos;
    }
  @Override
  public SeasonDto getSeason(Long seasonId, Long leagueId) {
    Optional<Season> season = seasonRepository.findByIdAndLeagueId(seasonId,leagueId);
    if(season.isPresent()) return seasonDtoMapper.apply(season.get());
    throw new EntityNotFoundException("season not found with ids: leagueId: " + leagueId + " seasonId: " + seasonId);
  }
    @Override
    public void updateSeason(SeasonDto seasonDto, Long seasonId, Long leagueId) {
        seasonRepository.findByIdAndLeagueId(seasonId,leagueId).ifPresent(seasonDb -> {
            seasonDb.setName(seasonDto.getName());
            seasonRepository.save(seasonDb);
        });
    }
    @Override
    public void deleteSeason( Long seasonId, Long leagueId) {
        seasonRepository.findByIdAndLeagueId(seasonId,leagueId).ifPresent(seasonDb -> {
            seasonDb.setIsDeleted(true);
            seasonRepository.save(seasonDb);
        });
    }

//    @Override
//    public void saveSeason(SeasonDto seasonDto) {
//        if (seasonDto == null || seasonDto.getLeague() == null) {
//            throw new IllegalArgumentException("Season DTO or League information cannot be null");
//        }
//
//        // Retrieve league ID from the SeasonDto
//        Long leagueId = seasonDto.getLeague().id();
//
//        // Retrieve the league from the repository
//        League league = leagueRepository.findById(leagueId)
//                .orElseThrow(() -> new EntityNotFoundException("League not found with id: " + leagueId));
//
//        // Create a new Season entity and set its attributes
//        Season season = new Season();
//        season.setName(seasonDto.getName());
//        season.setLeague(league);
//
//        // Save the season entity
//        seasonRepository.save(season);
//
//        // After saving the season, also associate it with the league
//        league.getSeasons().add(season);
//        leagueRepository.save(league); // Save the league to update the association
//    }
//
//
//
//    @Override
//    public void updateSeason(Long id, SeasonDto seasonDto) {
//        if (seasonDto == null) {
//            return;
//        }
//        Season seasonDb = seasonRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Season not found with id: " + id));
//
//        seasonDb.setName(seasonDto.getName());
//
//        seasonRepository.save(seasonDb);
//    }
//
//    @Override
//    public Optional<SeasonDto> getSeasonById(Long id) {
//        if (id == null || id <= 0) {
//            throw new IllegalArgumentException("Season id must be a positive non-zero value");
//        }
//
//        Optional<Season> seasonOptional = seasonRepository.findById(id);
//        if (seasonOptional.isPresent()) {
//            Season season = seasonOptional.get();
//
//            League league = leagueRepository.findBySeasonsContaining(season)
//                    .orElseThrow(() -> new EntityNotFoundException("League not found for season with id: " + id));
//
//            LeagueDTO leagueDto = new LeagueDTO(league.getId(), league.getName(), league.getStart_date(), league.getEnd_date(), league.getDescription());
//
//            SeasonDto seasonDto = seasonDtoMapper.apply(season);
//            seasonDto.setLeague(leagueDto);
//
//            return Optional.of(seasonDto);
//        } else {
//            return Optional.empty();
//        }
//    }
//
//
//    @Override
//    public List<SeasonDto> getAllSeasons() {
//        List<Season> seasons = seasonRepository.findAll();
//
//        // Sort seasons by insert date, with the newest on top
//        seasons.sort(Comparator.comparing(Season::getInsertDateTime).reversed());
//
//        return seasons.stream()
//                .map(season -> {
//                    SeasonDto seasonDto = seasonDtoMapper.apply(season);
//                    League league = leagueRepository.findBySeasonsContaining(season)
//                            .orElseThrow(() -> new EntityNotFoundException("League not found"));
//
//                    LeagueDTO leagueDto = new LeagueDTO(league.getId(), league.getName(), league.getStart_date(), league.getEnd_date(), league.getDescription());
//
//                    seasonDto.setLeague(leagueDto);
//                    return seasonDto;
//                })
//                .collect(Collectors.toList());
//    }
//
//
//    @Override
//    public void deleteSeason(Long id) {
//        Season seasonDb = seasonRepository.findById(id).get();
//        seasonDb.isDeleted = true;
//        seasonRepository.save(seasonDb);
//    }
//
//    @Override
//    public void createRoundForSeason(Long seasonId, RoundDto roundDto) throws ResourceNotFoundException {
//        Season season = seasonRepository.findById(seasonId)
//                .orElseThrow(() -> new ResourceNotFoundException("Season not found with id: " + seasonId));
//        Round round = roundService.createRound(seasonId, roundDto);
//        season.getRounds().add(round);
//        seasonRepository.save(season);
//    }
//
//    @Override
//    public List<RoundDto> getRoundsWithMatchesForSeason(Long seasonId) throws ResourceNotFoundException {
//        Season season = seasonRepository.findById(seasonId)
//                .orElseThrow(() -> new ResourceNotFoundException("Season not found with id: " + seasonId));
//
//        List<RoundDto> roundDtos = new ArrayList<>();
//        for (Round round : roundRepository.findRoundsBySeasonOrderByInsertDateTimeAsc(season)) {
//            RoundDto roundDto = roundDtoMapper.apply(round);
//            roundDtos.add(roundDto);
//        }
//        return roundDtos;
//    }


}
