<<<<<<< HEAD
import {PlayerDto} from "./player-dto";
export class PageResponseDto<T> {
  constructor(public content: T[],public pageNumber: number, public pageSize: number,
=======
export class PageResponseDto {
  constructor(public content: any[],public pageNumber: number, public pageSize: number,
>>>>>>> main
              public totalElements: number) {
  }
}
