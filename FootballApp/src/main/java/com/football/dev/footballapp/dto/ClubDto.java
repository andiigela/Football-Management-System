package com.football.dev.footballapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClubDto {
    private Long id;
    private String name;
    private Long userId;
}
