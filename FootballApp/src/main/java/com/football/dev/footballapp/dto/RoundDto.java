package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoundDto {
    private Long id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private List<Match> matches;
}
