import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {InjuryStatus} from "../../enums/injury-status";
import {ContractType} from "../../enums/contract-type";
import {InjuryService} from "../../services/injury.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ContractService} from "../../services/contract.service";
import {InjuryDto} from "../../common/injury-dto";
import {ContractDto} from "../../common/contract-dto";

@Component({
  selector: 'app-contract-edit',
  templateUrl: './contract-edit.component.html',
  styleUrl: './contract-edit.component.css'
})
export class ContractEditComponent implements OnInit {
  editContractForm: FormGroup;
  contractTypeList = Object.values(ContractType)
  currentPlayerId = 0;
  currentContractId = 0;
  constructor(private contractService: ContractService, private formBuilder: FormBuilder,
              private activatedRoute: ActivatedRoute,private router: Router) {
    this.editContractForm=formBuilder.group({
      startDate:[''],
      endDate:[''],
      salary:[0],
      contractType:['I_RREGULLT'],
    })
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(param => {
      this.currentPlayerId = param["id"];
      this.currentContractId = param["contractId"];
      this.getContract(this.currentPlayerId,this.currentContractId)
    })
  }
  editContract(){
    const formData = this.editContractForm.value;
    const contractDto = new ContractDto(formData.startDate,formData.endDate,formData.salary,formData.contractType);
    contractDto.id = this.currentContractId;
    this.contractService.editContract(this.currentPlayerId,contractDto).subscribe(()=>{
      this.router.navigate([`/players/${this.currentPlayerId}/contracts`])
    })
  }
  getContract(playerId: number, contractId: number){
    this.contractService.retrieveContract(playerId,contractId).subscribe(contract => {
      this.editContractForm.patchValue({
        startDate:contract.startDate,
        endDate:contract.endDate,
        salary:contract.salary,
        contractType:contract.contractType,
      });
    });
  }
}
