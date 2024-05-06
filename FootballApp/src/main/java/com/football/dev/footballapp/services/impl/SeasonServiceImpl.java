package com.football.dev.footballapp.services.impl;

import com.football.dev.footballapp.dto.ClubDto;
import com.football.dev.footballapp.dto.MatchDTO;
import com.football.dev.footballapp.dto.PlayerDto;
import com.football.dev.footballapp.dto.SeasonDto;
import com.football.dev.footballapp.mapper.MatchDTOMapper;
import com.football.dev.footballapp.mapper.SeasonDtoMapper;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Player;
import com.football.dev.footballapp.models.Season;
import com.football.dev.footballapp.models.enums.Foot;
import com.football.dev.footballapp.repository.MatchRepository;
import com.football.dev.footballapp.repository.SeasonRepository;
import com.football.dev.footballapp.services.SeasonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SeasonServiceImpl implements SeasonService {
    private final SeasonRepository seasonRepository;
    private final MatchRepository matchRepository;
    private final SeasonDtoMapper seasonDtoMapper;
    private final MatchDTOMapper matchDtoMapper;
    private final Function<ClubDto, Club> clubDtoToClub;

    public SeasonServiceImpl(SeasonRepository seasonRepository,
                             MatchRepository matchRepository,
                             SeasonDtoMapper seasonDtoMapper,
                             MatchDTOMapper matchDtoMapper, Function<ClubDto, Club> clubDtoToClub) {
        this.seasonRepository = seasonRepository;
        this.matchRepository = matchRepository;
        this.seasonDtoMapper = seasonDtoMapper;
        this.matchDtoMapper = matchDtoMapper;
        this.clubDtoToClub = clubDtoToClub;
    }

    @Override
    public void saveSeason(SeasonDto seasonDto) {
        seasonRepository.save(new Season(seasonDto.getName()));
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

            List<Match> matches = matchRepository.findBySeason(season);

            SeasonDto seasonDto = seasonDtoMapper.apply(season);
            seasonDto.setMatches(matches.stream().collect(Collectors.toList()));

            return Optional.of(seasonDto);
        } else {
            return Optional.empty();
        }
    }


    @Override
    public List<SeasonDto> getAllSeasons() {
        List<Season> seasons = seasonRepository.findAll();
        return seasons.stream()
                .map(season -> {
                    SeasonDto seasonDto = seasonDtoMapper.apply(season);
                    List<Match> matches = matchRepository.findBySeason(season);
                    seasonDto.setMatches(matches.stream().collect(Collectors.toList()));
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
    public List<Club> getClubsBySeasonId(Long seasonId) {
        List<Match> matches = matchRepository.findBySeasonId(seasonId);
        List<Club> clubs = new ArrayList<>();
        for (Match match : matches) {
            if (match.getHomeTeamId() != null) {
                clubs.add(match.getHomeTeamId());
            }
            if (match.getAwayTeamId() != null) {
                clubs.add(match.getAwayTeamId());
            }
        }
        return clubs;
    }
}
