import {PlayerDto} from "./player-dto";
export class PageResponseDto {
  constructor(public content: PlayerDto[],public pageNumber: number, public pageSize: number,
              public totalElements: number) {
  }
}
