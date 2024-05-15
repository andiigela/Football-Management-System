import {ContractType} from "../enums/contract-type";

export class ContractDto {
  public id:number=0;
  constructor(public startDate: Date, public endDate: Date, public salary: number, public contractType: ContractType) {
  }
}
