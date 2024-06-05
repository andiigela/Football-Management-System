import {ClubDto} from "./club-dto";
import {SeasonDto} from "./season-dto";

export interface StandingDTO {
  id:number,
  club:ClubDto,
  matchesPlayed:number,
  wonPlayed:number,
   drawMatches:number,
   lostMatches:number,
   goalScored:number,
   goalConceded:number,
   points:number,
   seasonId:SeasonDto
}
