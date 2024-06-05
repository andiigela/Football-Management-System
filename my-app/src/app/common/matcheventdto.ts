import {ClubDto} from "./club-dto";
import {PlayerDto} from "./player-dto";
import {MatchDto} from "./match-dto";

export class Matcheventdto {
  id: number;
  minute: number;
  type: string;
  clubId: ClubDto;
  player: PlayerDto;
  assisted: PlayerDto;
  substitutePlayer: PlayerDto;
  isPenalty: boolean;
  isOwnGoal: boolean;
  redCard: boolean;
  yellowCard: boolean;
  description: string;
  matchId: MatchDto;

  constructor(
    id: number,
    minute: number,
    type: string,
    clubId: ClubDto,
    player: PlayerDto,
    assisted: PlayerDto,
    substitutePlayer: PlayerDto,
    isPenalty: boolean,
    isOwnGoal: boolean,
    redCard: boolean,
    yellowCard: boolean,
    description: string,
    matchId: MatchDto
  ) {
    this.id = id;
    this.minute = minute;
    this.type = type;
    this.clubId = clubId;
    this.player = player;
    this.assisted = assisted;
    this.substitutePlayer = substitutePlayer;
    this.isPenalty = isPenalty;
    this.isOwnGoal = isOwnGoal;
    this.redCard = redCard;
    this.yellowCard = yellowCard;
    this.description = description;
    this.matchId = matchId;
  }

}
