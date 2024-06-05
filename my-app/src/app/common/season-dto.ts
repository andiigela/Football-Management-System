import {MatchDto} from "./match-dto";

export class SeasonDto {
  public id =0;
  public deleted: boolean = false;
  public currentSeason: boolean = false;
    constructor(public name: string,public start_date:Date,public end_date:Date,public headToHead:number,public numberOfStandings:number, public leagueId: number) {
  }
}
