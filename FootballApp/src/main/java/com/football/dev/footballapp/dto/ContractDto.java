package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.enums.ContractType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContractDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
    private ContractType contractType;

}
