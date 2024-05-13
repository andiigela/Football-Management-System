package com.football.dev.footballapp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoundDto {
    private Long id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;

}
