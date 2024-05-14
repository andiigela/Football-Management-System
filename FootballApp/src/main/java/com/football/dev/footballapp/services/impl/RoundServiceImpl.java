package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.mapper.RoundDtoMapper;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Round;
import com.football.dev.footballapp.models.Season;
import com.football.dev.footballapp.repository.ClubRepository;
import com.football.dev.footballapp.repository.MatchRepository;
import com.football.dev.footballapp.repository.RoundRepository;
import com.football.dev.footballapp.repository.SeasonRepository;
import com.football.dev.footballapp.services.RoundService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class RoundServiceImpl implements RoundService {
    private final RoundRepository roundRepository;
    private final RoundDtoMapper roundDtoMapper;
    private final ClubRepository clubRepository;
    private final MatchRepository matchRepository;
    private final SeasonRepository seasonRepository;

    public RoundServiceImpl(RoundRepository roundRepository,
                            RoundDtoMapper roundDtoMapper,
                            ClubRepository clubRepository,
                            MatchRepository matchRepository,
                            SeasonRepository seasonRepository) {
        this.roundRepository = roundRepository;
        this.roundDtoMapper = roundDtoMapper;
        this.clubRepository = clubRepository;
        this.matchRepository = matchRepository;
        this.seasonRepository = seasonRepository;
    }

    @Override
    public Optional<RoundDto> getRoundById(Long id) {
        return roundRepository.findById(id).map(roundDtoMapper);
    }

    @Override
    public Round createRound(Long seasonId, RoundDto roundDto) {
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found with id: " + seasonId));

        Round round = new Round(roundDto.getStart_date(), roundDto.getEnd_date());
        round.setSeason(season);
        Round savedRound = roundRepository.save(round);
        List<Match> randomMatches = generateRandomMatches(round);

        randomMatches.forEach(match -> {
            match.setRound(savedRound);
            matchRepository.save(match);
        });

        return savedRound;
    }


    private List<Match> generateRandomMatches(Round round) {
        List<Match> randomMatches = new ArrayList<>();
        Random random = new Random();
        List<Club> assignedClubs = new ArrayList<>(); // List to keep track of clubs assigned to matches
        LocalDateTime startDate = round.getStart_date();
        LocalDateTime endDate = round.getEnd_date();

        for (int i = 0; i < 5; i++) {
            Match randomMatch = new Match();
            Club homeTeam;
            Club awayTeam;
            do {
                homeTeam = getRandomClub();
                awayTeam = getRandomClub();
            } while (homeTeam.getId().equals(awayTeam.getId()) || // Ensure home team and away team are different
                    assignedClubs.contains(homeTeam) ||          // Ensure home team is not already assigned
                    assignedClubs.contains(awayTeam));           // Ensure away team is not already assigned

            assignedClubs.add(homeTeam); // Mark home team as assigned
            assignedClubs.add(awayTeam); // Mark away team as assigned
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

    private LocalDateTime getRandomDateBetween(LocalDateTime start, LocalDateTime end, Random random) {
        long startEpochDay = start.toLocalDate().toEpochDay();
        long endEpochDay = end.toLocalDate().toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
        return LocalDateTime.of(LocalDate.ofEpochDay(randomEpochDay), LocalTime.of(random.nextInt(24), random.nextInt(60)));
    }


    private Club getRandomClub() {
        List<Club> clubs = clubRepository.findAll();
        int randomIndex = new Random().nextInt(clubs.size());
        return clubs.get(randomIndex);
    }
    @Override
    public List<MatchDTO> getMatchesByRoundId(Long roundId) {
        Optional<Round> roundOptional = roundRepository.findById(roundId);
        if (roundOptional.isPresent()) {
            Round round = roundOptional.get();
            List<Match> matches = matchRepository.findByRound(round);
            return matches.stream()
                    .map(match -> {
                        Club homeTeam = clubRepository.findById(match.getHomeTeamId().getId()).orElse(null);
                        Club awayTeam = clubRepository.findById(match.getAwayTeamId().getId()).orElse(null);

                        return new MatchDTO(
                                match.getId(),
                                new ClubDto(homeTeam != null ? homeTeam.getId() : null,
                                        homeTeam != null ? homeTeam.getName() : null,
                                        homeTeam != null ? homeTeam.getFoundedYear() : null,
                                        homeTeam != null ? homeTeam.getCity() : null,
                                        homeTeam != null ? homeTeam.getWebsite() : null),
                                new ClubDto(awayTeam != null ? awayTeam.getId() : null,
                                        awayTeam != null ? awayTeam.getName() : null,
                                        awayTeam != null ? awayTeam.getFoundedYear() : null,
                                        awayTeam != null ? awayTeam.getCity() : null,
                                        awayTeam != null ? awayTeam.getWebsite() : null),
                                match.getMatchDate(),
                                match.getStadium(),
                                match.getResult(),
                                match.getHomeTeamScore(),
                                match.getAwayTeamScore()
                        );
                    })
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }


}
