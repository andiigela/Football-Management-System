import {ClubDto} from "./club-dto";

export class MatchDto {
  id: number;
  homeTeam: ClubDto;
  awayTeam: ClubDto;
  matchDate: Date;
  result: string;
  homeTeamScore: number;
  awayTeamScore: number;

  constructor(
    id: number,
    homeTeam: ClubDto,
    awayTeam: ClubDto,
    matchDate: Date,
    result: string,
    homeTeamScore: number,
    awayTeamScore: number
  ) {
    this.id = id;
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
    this.matchDate = matchDate;
    this.result = result;
    this.homeTeamScore = homeTeamScore;
    this.awayTeamScore = awayTeamScore;
  }
}
