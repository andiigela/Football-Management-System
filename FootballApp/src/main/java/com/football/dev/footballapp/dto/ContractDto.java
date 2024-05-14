package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.enums.ContractType;

import java.time.LocalDate;

public class ContractDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
    private ContractType contractType;

}
