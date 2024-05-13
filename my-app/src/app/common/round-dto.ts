import { MatchDto } from "./match-dto";

export class RoundDto {
  id: number;
  start_date: Date;
  end_date: Date;
  matches: MatchDto[];

  constructor(id: number, start_date: Date, end_date: Date, matches: MatchDto[]) {
    this.id = id;
    this.start_date = start_date;
    this.end_date = end_date;
    this.matches = matches;
  }
}
