package com.football.dev.footballapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RoundDto {
    private Long id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private List<MatchDTO> matches;

    // Constructors
    public RoundDto() {}

    public RoundDto(Long id, LocalDateTime start_date, LocalDateTime end_date, List<MatchDTO> matches) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.matches = matches;
    }

    // Getters and setters (omitted for brevity)
}
