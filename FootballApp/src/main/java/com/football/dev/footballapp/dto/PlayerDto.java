package com.football.dev.footballapp.dto;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerDto {
    private String name;
    private Double height;
    private Double weight;
    private Integer shirtNumber;
    private String preferredFoot;
    private String position;
}
