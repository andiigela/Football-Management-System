export class TransferDto {
   id:number ;
  player:string;
  previousClub:string;
   newClub:string;
   transferDate:Date;
   transferFee:number;

  constructor(id: number, player: string, previousClub: string, newClub: string, transferDate: Date, transferFee: number) {
    this.id = id;
    this.player = player;
    this.previousClub = previousClub;
    this.newClub = newClub;
    this.transferDate = transferDate;
    this.transferFee = transferFee;
  }
}
