import {MatchDto} from "./match-dto";

export class SeasonDto {
  public id =0;
  public deleted: boolean = false;
    constructor(public name: string, public leagueId: number) {
  }
}
