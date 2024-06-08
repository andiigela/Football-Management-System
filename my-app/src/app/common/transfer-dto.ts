import {PlayerDto} from "./player-dto";
import {ClubDto} from "./club-dto";

export class TransferDto {
   id:number ;
    player:PlayerDto;
   previousClub:ClubDto;
   newClub:ClubDto;
   transferDate:Date;
   transferFee:number;

  constructor(id: number, player:PlayerDto, previousClub: ClubDto, newClub: ClubDto, transferDate: Date, transferFee: number) {
    this.id = id;
    this.player = player;
    this.previousClub = previousClub;
    this.newClub = newClub;
    this.transferDate = transferDate;
    this.transferFee = transferFee;
  }
}
