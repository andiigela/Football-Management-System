export class PageResponseDto<T> {
  constructor(public content: T[],public pageNumber: number, public pageSize: number,
              public totalElements: number) {
  }
}
