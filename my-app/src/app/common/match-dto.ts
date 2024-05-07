export class MatchDto {
    id: number;
    homeTeamId?: { id: number, name: string };
    awayTeamId?: { id: number, name: string };
    matchDate: Date;
    stadium?: { id: number, name: string };
    result: string;
    homeTeamScore: number;
    awayTeamScore: number;

    constructor(
        id: number,
        homeTeamId: { id: number, name: string },
        awayTeamId: { id: number, name: string },
        matchDate: Date,
        stadium: { id: number, name: string },
        result: string,
        homeTeamScore: number,
        awayTeamScore: number
    ) {
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
