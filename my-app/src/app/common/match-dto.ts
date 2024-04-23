export class MatchDto {
   id : number;
   homeTeamId: string ;
   awayTeamId:string ;
   matchDate:Date;
   stadium:string;
   result:string;
   homeTeamScore:number;
   awayTeamScore:number;

  constructor(id: number, homeTeamId: string, awayTeamId: string, matchDate: Date, stadium: string, result: string, homeTeamScore: number, awayTeamScore: number) {
    this.id = id;
    this.homeTeamId = homeTeamId;
    this.awayTeamId = awayTeamId;
    this.matchDate = matchDate;
    this.stadium = stadium;
    this.result = result;
    this.homeTeamScore = homeTeamScore;
    this.awayTeamScore = awayTeamScore;
  }
}
