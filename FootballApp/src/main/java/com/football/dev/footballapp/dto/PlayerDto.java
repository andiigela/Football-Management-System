package com.football.dev.footballapp.dto;
import com.football.dev.footballapp.models.Contract;
import com.football.dev.footballapp.models.Player;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerDto {

    private Function<Contract,ContractDto> contractDtoMapper;
    private Long dbId;

//    private Function<Contract,ContractDto> contractDtoMapper;
//    private Long id;

    private String name;
    private Double height;
    private Double weight;
    private Integer shirtNumber;
    private String imagePath;
    private String preferred_foot;
    private String position;
    private List<ContractDto> contracts;

    public PlayerDto(Long dbId, String name, Double height, Double weight, Integer shirtNumber, String imagePath,
                     String preferred_foot, String position,
                     List<Contract> contracts,Function<Contract,ContractDto> contractDtoMapper) {
        this.dbId = dbId;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.shirtNumber = shirtNumber;
        this.imagePath = imagePath;
        this.preferred_foot = preferred_foot;
        this.position = position;
        this.contracts = this.convertContracts(contracts,contractDtoMapper);
    }
    private List<ContractDto> convertContracts(List<Contract> contracts,Function<Contract,ContractDto> contractDtoMapper){
        if(contracts == null) return null;
        return contracts.stream().map(contractDtoMapper).collect(Collectors.toList());
    }
}
