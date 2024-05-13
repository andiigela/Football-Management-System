package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.*;
import com.football.dev.footballapp.exceptions.ResourceNotFoundException;
import com.football.dev.footballapp.mapper.MatchDTOMapper;
import com.football.dev.footballapp.mapper.RoundDtoMapper;
import com.football.dev.footballapp.mapper.SeasonDtoMapper;
import com.football.dev.footballapp.models.*;
import com.football.dev.footballapp.models.enums.Foot;
import com.football.dev.footballapp.repository.LeagueRepository;
import com.football.dev.footballapp.repository.MatchRepository;
import com.football.dev.footballapp.repository.RoundRepository;
import com.football.dev.footballapp.repository.SeasonRepository;
import com.football.dev.footballapp.services.RoundService;
import com.football.dev.footballapp.services.SeasonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SeasonServiceImpl implements SeasonService {
    private final SeasonRepository seasonRepository;
    private final LeagueRepository leagueRepository;
    private final SeasonDtoMapper seasonDtoMapper;
    private final RoundService roundService;
    private final RoundDtoMapper roundDtoMapper;
    public SeasonServiceImpl(SeasonRepository seasonRepository,
                             SeasonDtoMapper seasonDtoMapper,
                             LeagueRepository leagueRepository,
                             RoundService roundService,
                             RoundDtoMapper roundDtoMapper) {
        this.seasonRepository = seasonRepository;
        this.seasonDtoMapper = seasonDtoMapper;
        this.leagueRepository = leagueRepository;
        this.roundService = roundService;
        this.roundDtoMapper = roundDtoMapper;
    }
    @Override
    public void saveSeason(SeasonDto seasonDto) {
        if (seasonDto == null || seasonDto.getLeague() == null) {
            throw new IllegalArgumentException("Season DTO or League information cannot be null");
        }

        // Retrieve league ID from the SeasonDto
        Long leagueId = seasonDto.getLeague().id();

        // Retrieve the league from the repository
        League league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new EntityNotFoundException("League not found with id: " + leagueId));

        // Create a new Season entity and set its attributes
        Season season = new Season();
        season.setName(seasonDto.getName());
        season.setLeague(league);

        // Save the season entity
        seasonRepository.save(season);

        // After saving the season, also associate it with the league
        league.getSeasons().add(season);
        leagueRepository.save(league); // Save the league to update the association
    }



    @Override
    public void updateSeason(Long id, SeasonDto seasonDto) {
        if (seasonDto == null) {
            return;
        }
        Season seasonDb = seasonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Season not found with id: " + id));

        seasonDb.setName(seasonDto.getName());

        seasonRepository.save(seasonDb);
    }

    @Override
    public Optional<SeasonDto> getSeasonById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Season id must be a positive non-zero value");
        }

        Optional<Season> seasonOptional = seasonRepository.findById(id);
        if (seasonOptional.isPresent()) {
            Season season = seasonOptional.get();

            League league = leagueRepository.findBySeasonsContaining(season)
                    .orElseThrow(() -> new EntityNotFoundException("League not found for season with id: " + id));

            LeagueDTO leagueDto = new LeagueDTO(league.getId(), league.getName(), league.getStart_date(), league.getEnd_date(), league.getDescription());

            SeasonDto seasonDto = seasonDtoMapper.apply(season);
            seasonDto.setLeague(leagueDto);

            return Optional.of(seasonDto);
        } else {
            return Optional.empty();
        }
    }


    @Override
    public List<SeasonDto> getAllSeasons() {
        List<Season> seasons = seasonRepository.findAll();

        // Sort seasons by insert date, with the newest on top
        seasons.sort(Comparator.comparing(Season::getInsertDateTime).reversed());

        return seasons.stream()
                .map(season -> {
                    SeasonDto seasonDto = seasonDtoMapper.apply(season);
                    League league = leagueRepository.findBySeasonsContaining(season)
                            .orElseThrow(() -> new EntityNotFoundException("League not found"));

                    LeagueDTO leagueDto = new LeagueDTO(league.getId(), league.getName(), league.getStart_date(), league.getEnd_date(), league.getDescription());

                    seasonDto.setLeague(leagueDto);
                    return seasonDto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public void deleteSeason(Long id) {
        Season seasonDb = seasonRepository.findById(id).get();
        if (seasonDb == null) throw new EntityNotFoundException("Season not found with specified id: " + id);
        seasonDb.isDeleted = true;
        seasonRepository.save(seasonDb);
    }

    @Override
    public void createRoundForSeason(Long seasonId, RoundDto roundDto) throws ResourceNotFoundException {
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found with id: " + seasonId));

        Round round = roundService.createRound(roundDto);

        season.getRounds().add(round);

        seasonRepository.save(season);
    }

    @Override
    public List<RoundDto> getRoundsWithMatchesForSeason(Long seasonId) throws ResourceNotFoundException {
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found with id: " + seasonId));

        List<RoundDto> roundDtos = new ArrayList<>();

        for (Round round : season.getRounds()) {
            RoundDto roundDto = roundDtoMapper.apply(round); // Use RoundDtoMapper to map Round to RoundDto
            roundDtos.add(roundDto);
        }

        return roundDtos;
    }


}
