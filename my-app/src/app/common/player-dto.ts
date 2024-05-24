import {ContractDto} from "./contract-dto";

export class PlayerDto {
  public id =0;
  public imagePath: string = "";
  public contracts: ContractDto[]|null=null;
  public permissionSent: boolean=false;
  constructor(public name: string, public height: number,public weight: number,public shirtNumber:number,public preferred_foot: string,public position: string) {
  }
}
