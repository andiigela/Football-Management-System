import {InjuryStatus} from "../enums/injury-status";

export class InjuryDto {
    public id =0;
    constructor(public injuryType: string, public injuryDate: Date,public expectedRecoveryTime: Date,public injuryStatus: InjuryStatus) {
    }
}
