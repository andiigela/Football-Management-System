package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.mapper.RoundDtoMapper;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.ES.MatchES;
import com.football.dev.footballapp.models.ES.RoundES;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Round;
import com.football.dev.footballapp.models.Season;
import com.football.dev.footballapp.models.enums.MatchStatus;
import com.football.dev.footballapp.repository.esrepository.MatchRepositoryES;
import com.football.dev.footballapp.repository.esrepository.RoundRepositoryES;
import com.football.dev.footballapp.repository.jparepository.RoundRepository;
import com.football.dev.footballapp.repository.jparepository.ClubRepository;
import com.football.dev.footballapp.repository.jparepository.MatchRepository;
import com.football.dev.footballapp.repository.jparepository.SeasonRepository;
import com.football.dev.footballapp.services.RoundService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RoundServiceImpl implements RoundService {
    private final RoundRepository roundRepository;
    private final RoundRepositoryES roundRepositoryES;
    private final RoundDtoMapper roundDtoMapper;
    private final ClubRepository clubRepository;
    private final MatchRepository matchRepository;
     private final MatchRepositoryES matchRepositoryES;
     private final SeasonRepository seasonRepository;

    public RoundServiceImpl(RoundRepository roundRepository, RoundRepositoryES roundRepositoryES, RoundDtoMapper roundDtoMapper, ClubRepository clubRepository, MatchRepository matchRepository, MatchRepositoryES matchRepositoryES, SeasonRepository seasonRepository) {
        this.roundRepository = roundRepository;
        this.roundRepositoryES = roundRepositoryES;
        this.roundDtoMapper = roundDtoMapper;
        this.clubRepository = clubRepository;
        this.matchRepository = matchRepository;
        this.matchRepositoryES = matchRepositoryES;
        this.seasonRepository = seasonRepository;
    }


//    @Override
//    public void saveRound(RoundDto roundDto, Long seasonId) {
//      if (roundDto == null) throw new IllegalArgumentException("roundDto cannot be null");
//      Season seasonDb = seasonRepository.findById(seasonId).orElseThrow(() -> new EntityNotFoundException("Season not found with id: " + seasonId));
//      Round round = new Round(roundDto.getStart_date(), roundDto.getEnd_date(), seasonDb);
//      List<Match> generatedMatches = generateRandomMatches(round);
//      round.setMatches(generatedMatches);
//      Round savedRound = roundRepository.save(round);
//
//
//        String esId = UUID.randomUUID().toString();
//        RoundES roundES = new RoundES();
//        roundES.setId(esId);
//        roundES.setDbId(savedRound.getId());
//        roundES.setStartDate(savedRound.getStart_date());
//        roundES.setEndDate(savedRound.getEnd_date());
//        roundES.setSeason(seasonDb.id);
//        roundRepositoryES.save(roundES);
//
//        for (Match match : generatedMatches) {
//            match.setRound(savedRound);
//            matchRepository.save(match);
//            MatchES matchES = createMatchES(match);
//            matchRepositoryES.save(matchES);
//        }
//
//    }
    private MatchES createMatchES(Match match) {
        String esId = UUID.randomUUID().toString();
        MatchES matchES = new MatchES();
        matchES.setId(esId);
        matchES.setDbId(match.getId());
        matchES.setMatchDate(match.getMatchDate());
        matchES.setHomeTeamScore(match.getHomeTeamScore());
        matchES.setAwayTeamScore(match.getAwayTeamScore());
        return matchES;
    }


  @Override
    public Page<RoundDto> retrieveRounds(Long seasonId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Round> roundPage = roundRepository.findRoundsBySeasonIdOrderByInsertDateTimeDesc(seasonId, pageRequest);
        return roundPage.map(roundDtoMapper);
    }
    @Override
    public RoundDto getRound(Long roundId,Long seasonId) {
        Optional<Round> round = roundRepository.findByIdAndSeasonId(roundId,seasonId);
        if(round.isPresent()) return roundDtoMapper.apply(round.get());
        throw new EntityNotFoundException("Round not found with ids: seasonId: " + seasonId + " roundId: " + roundId);
    }
    @Override
    public void updateRound(RoundDto roundDto, Long roundId, Long seasonId) {
        roundRepository.findByIdAndSeasonId(roundId,seasonId).ifPresent(roundDb -> {
            roundDb.setStart_date(roundDto.getStart_date());
            roundDb.setEnd_date(roundDto.getEnd_date());
            roundRepository.save(roundDb);
        });
    }
    @Override
    public void deleteRound(Long roundId, Long seasonId) {
        roundRepository.findByIdAndSeasonId(roundId,seasonId).ifPresent(roundDb -> {
            roundDb.setIsDeleted(true);
            roundRepository.save(roundDb);
        });
    }

  public List<Round> generateRoundsAndSave(List<Club> clubs, int gamesPerRound, LocalDateTime startDate, Season season) {

      List<Round> rounds = new ArrayList<>();
    Random random = new Random();
    int totalClubs = clubs.size();
    int totalMatches = (totalClubs * (totalClubs - 1));

    int numberOfRounds = totalMatches / gamesPerRound;

    List<List<Club>> rotationSchedule = new ArrayList<>();
    for (int i = 0; i < numberOfRounds; i++) {
      List<Club> roundSchedule = new ArrayList<>();
      for (int j = 0; j < totalClubs; j++) {
        roundSchedule.add(clubs.get((i + j) % totalClubs));
      }
      rotationSchedule.add(roundSchedule);
    }


    for (int i = 0; i < numberOfRounds; i++) {
      LocalDateTime roundStartDate = startDate.plusDays(i * 7L);
      LocalDateTime roundEndDate = roundStartDate.plusDays(6);

      Round round = new Round();
      round.setName("Round " + (i + 1));
      round.setStart_date(roundStartDate);
      round.setEnd_date(roundEndDate);
      round.setSeason(season);


      List<Match> matches = new ArrayList<>();

      for (int j = 0; j < gamesPerRound; j++) {
        Club homeClub = rotationSchedule.get(i).get(j);
        Club awayClub;

        do {
          int randomAwayClubIndex = random.nextInt(totalClubs);
          awayClub = rotationSchedule.get(i).get(randomAwayClubIndex);
        } while (awayClub.equals(homeClub));

        int randomDays = random.nextInt(3);
        LocalDateTime matchDate = roundStartDate.plusDays(randomDays);

        Match match = new Match();
        match.setHomeTeamId(homeClub);
        match.setAwayTeamId(awayClub);
        match.setMatchDate(matchDate);
        match.setRound(round);
        match.setAwayTeamScore(0);
        match.setHomeTeamScore(0);
        match.setMatchStatus(MatchStatus.SCHEDULED);
        matches.add(match);

        MatchES matchES = createMatchES(match);
        matchRepositoryES.save(matchES);
      }


      Round roundSaved = roundRepository.save(round);

      RoundES roundES= createRoundES(roundSaved,season.getId());

      roundRepositoryES.save(roundES);
      matchRepository.saveAll(matches);

      round.setMatches(matches);


      rounds.add(round);
    }

    return rounds;
  }
  private RoundES createRoundES(Round savedRound, Long seasonId){
    Season seasonDb = seasonRepository.findById(seasonId).orElseThrow(() -> new EntityNotFoundException("Season not found with id: " + seasonId));
    String esId = UUID.randomUUID().toString();
    RoundES roundES = new RoundES();
    roundES.setId(esId);
    roundES.setDbId(savedRound.getId());
    roundES.setStartDate(savedRound.getStart_date());
    roundES.setEndDate(savedRound.getEnd_date());
    roundES.setSeason(seasonDb.id);
    roundRepositoryES.save(roundES);
    return roundES;
  }


  @Override
  public void saveRounds(List<Round> rounds) {
    roundRepository.saveAll(rounds);
  }


//    @Override
//    public Optional<RoundDto> getRoundById(Long id) {
//        return roundRepository.findById(id).map(roundDtoMapper);
//    }

//    @Override
//    public Round createRound(Long seasonId, RoundDto roundDto) {
//        Season season = seasonRepository.findById(seasonId)
//                .orElseThrow(() -> new ResourceNotFoundException("Season not found with id: " + seasonId));
//
//        Round round = new Round(roundDto.getStart_date(), roundDto.getEnd_date());
//        round.setSeason(season);
//        Round savedRound = roundRepository.save(round);
//        List<Match> randomMatches = generateRandomMatches(round);
//
//        randomMatches.forEach(match -> {
//            match.setRound(savedRound);
//            matchRepository.save(match);
//        });
//
//        return savedRound;
//    }

    @Override
    public Page<RoundES> findRoundsByStartDateBetween(Date startDate, Date endDate, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return roundRepositoryES.findRoundsByStartDateBetween(startDate, endDate, pageable);
    }

    private List<Match> generateRandomMatches(Round round) {
        List<Match> randomMatches = new ArrayList<>();
        Random random = new Random();
        List<Club> availableClubs = clubRepository.findAll();
        LocalDateTime startDate = round.getStart_date();
        LocalDateTime endDate = round.getEnd_date();

        for (int i = 0; i < 5; i++) {
            Match randomMatch = new Match();
            Club homeTeam;
            Club awayTeam;
            do {
                homeTeam = getRandomClub(availableClubs, random);
                awayTeam = getRandomClub(availableClubs, random);
            } while (homeTeam.getId().equals(awayTeam.getId())); // Ensure home team and away team are different

            availableClubs.remove(homeTeam); // Remove selected clubs from available clubs list
            availableClubs.remove(awayTeam);

            randomMatch.setHomeTeamId(homeTeam);
            randomMatch.setAwayTeamId(awayTeam);
            randomMatch.setMatchDate(getRandomDateBetween(startDate, endDate, random));
            randomMatch.setStadium(null);
            randomMatch.setResult("Pending");
            randomMatch.setHomeTeamScore(0);
            randomMatch.setAwayTeamScore(0);
            randomMatches.add(randomMatch);
        }
        return randomMatches;
    }

    private Club getRandomClub(List<Club> availableClubs, Random random) {
        int randomIndex = random.nextInt(availableClubs.size());
        return availableClubs.get(randomIndex);
    }

    private LocalDateTime getRandomDateBetween(LocalDateTime start, LocalDateTime end, Random random) {
        long startEpochDay = start.toLocalDate().toEpochDay();
        long endEpochDay = end.toLocalDate().toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
        return LocalDateTime.of(LocalDate.ofEpochDay(randomEpochDay), LocalTime.of(random.nextInt(24), random.nextInt(60)));
    }



}
