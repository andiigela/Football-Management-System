package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.RoundDto;
import com.football.dev.footballapp.mapper.RoundDtoMapper;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Round;
import com.football.dev.footballapp.repository.ClubRepository;
import com.football.dev.footballapp.repository.MatchRepository;
import com.football.dev.footballapp.repository.RoundRepository;
import com.football.dev.footballapp.services.RoundService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RoundServiceImpl implements RoundService {
    private final RoundRepository roundRepository;
    private final RoundDtoMapper roundDtoMapper;
    private final ClubRepository clubRepository;
    private final MatchRepository matchRepository;

    public RoundServiceImpl(RoundRepository roundRepository,
                            RoundDtoMapper roundDtoMapper,
                            ClubRepository clubRepository,
                            MatchRepository matchRepository) {
        this.roundRepository = roundRepository;
        this.roundDtoMapper = roundDtoMapper;
        this.clubRepository = clubRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public Optional<RoundDto> getRoundById(Long id) {
        return roundRepository.findById(id).map(roundDtoMapper);
    }

    @Override
    public Round createRound(RoundDto roundDto) {
        List<Match> randomMatches = generateRandomMatches();
        Round round = new Round(roundDto.getStart_date(), roundDto.getEnd_date(), roundDto.getSeason(), randomMatches);
        Round savedRound = roundRepository.save(round);
        randomMatches.forEach(match -> match.setRound(savedRound));
        matchRepository.saveAll(randomMatches);
        return savedRound; // Return the created round
    }

    private List<Match> generateRandomMatches() {
        List<Match> randomMatches = new ArrayList<>();
        Random random = new Random();
        List<Club> assignedClubs = new ArrayList<>(); // List to keep track of clubs assigned to matches
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
            randomMatch.setMatchDate(LocalDateTime.now().plusDays(random.nextInt(30))); // Random date within next 30 days
            randomMatch.setStadium(null);
            randomMatch.setResult("Pending");
            randomMatch.setHomeTeamScore(0);
            randomMatch.setAwayTeamScore(0);
            randomMatches.add(randomMatch);
        }
        return randomMatches;
    }



    private Club getRandomClub() {
        List<Club> clubs = clubRepository.findAll();
        int randomIndex = new Random().nextInt(clubs.size());
        return clubs.get(randomIndex);
    }


}
