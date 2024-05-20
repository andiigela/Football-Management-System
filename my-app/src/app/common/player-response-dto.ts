import {ContractDto} from "./contract-dto";

export class PlayerResponseDto {
    public imagePath: string = "";
    public contracts: ContractDto[]|null=null;
    constructor(public id:number,public name: string, public height: number,public weight: number,public shirtNumber:number,public preferred_foot: string,public position: string) {
    }
}
