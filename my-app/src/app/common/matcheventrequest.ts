export class Matcheventrequest {
  constructor(public id:number,public minute:number,public type:string,public club_id:number,public player_id:number,public assist_id:number,public subsitutePlayer_id:number,public isPenalty:boolean,public isOwnGoal:boolean,public isRedCard:boolean,public isYellowCard:boolean,public description:string,public match_id:number ) {
  }
}
