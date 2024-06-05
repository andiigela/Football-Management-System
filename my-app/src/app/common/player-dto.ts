import {ContractDto} from "./contract-dto";
import {ClubDto} from "./club-dto";

export class PlayerDto {
  public id = 0;
  public dbId =0;
  public imagePath: string = "";
  public contracts: ContractDto[] | null = null;
  public permissionSent: boolean = false;
  public club: ClubDto | undefined;

  constructor(
    public name: string,
    public height: number,
    public weight: number,
    public shirtNumber: number,
    public preferred_foot: string,
    public position: string,
    public clubId: number
  ) {
  }
}
