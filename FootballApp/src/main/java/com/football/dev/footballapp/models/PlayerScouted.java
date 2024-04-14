package com.football.dev.footballapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//Dmth ky model i permban basic info te player dhe nje raport qe i eshte bere player nga scouts
@Entity
@Table(name = "player_scouted")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PlayerScouted extends BaseEntity{
    //player details
    private String playerName;
    private String playerSurname;
    private int playerAge;
    private double playerWeight;
    private double playerHeight;
    private String playerPosition;
    @OneToMany(mappedBy = "playerScouted", cascade = CascadeType.ALL)
    private List<Injuries> injuries;
    //scouting details
    @Lob
    private String report; /*A scouting report typically includes detailed observations and
                            assessments of a player's performance, skills, strengths, weaknesses,
                            and overall potential. Mundemi me rujt ne noSQL*/
}
