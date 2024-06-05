import {PlayerDto} from "./player-dto";

export interface ClubDto {
  id: number;
  name: string;
  foundedYear: number;
  city: string;
  website: string;
  players:PlayerDto[]
}
