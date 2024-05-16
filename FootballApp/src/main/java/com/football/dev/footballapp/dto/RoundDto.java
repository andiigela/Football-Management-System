package com.football.dev.footballapp.dto;


import com.football.dev.footballapp.models.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class RoundDto {
    private Long id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private List<MatchDTO> matches;
    private Function<Match, MatchDTO> matchDtoMapper;

    public RoundDto(Long id, LocalDateTime start_date,
                    LocalDateTime end_date, List<Match> matches,
                    Function<Match, MatchDTO> matchDtoMapper) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.matches = this.convertMatches(matches, matchDtoMapper);
    }

    private List<MatchDTO> convertMatches(List<Match> matches, Function<Match,MatchDTO> matchDtoMapper){
        if(matches == null) return null;
        return matches.stream().map(matchDtoMapper).collect(Collectors.toList());
    }
}
