package com.football.dev.footballapp.dto;
import lombok.Data;
@Data
public class PlayerIdDto {
    private Long id;
    public PlayerIdDto(Long id){
        this.id=id;
    }
}
