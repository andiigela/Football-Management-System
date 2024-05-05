export class PageResponseDto {
  constructor(public content: any[],public pageNumber: number, public pageSize: number,
              public totalElements: number) {
  }
}
