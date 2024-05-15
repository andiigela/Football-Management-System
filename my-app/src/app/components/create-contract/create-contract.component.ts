import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ContractDto} from "../../common/contract-dto";
import {ActivatedRoute, Router} from "@angular/router";
import {ContractService} from "../../services/contract.service";
import {ContractType} from "../../enums/contract-type";

@Component({
  selector: 'app-create-contract',
  templateUrl: './create-contract.component.html',
  styleUrl: './create-contract.component.css'
})
export class CreateContractComponent implements OnInit {
  contractForm: FormGroup;
  contractTypeList = Object.values(ContractType);
  currentPlayerId = 0;
  constructor(private formBuilder: FormBuilder,private activatedRoute: ActivatedRoute,
              private contractService: ContractService,private router: Router) {
    this.contractForm = this.formBuilder.group({
      startDate:[''],
      endDate:[''],
      salary:[0],
      contractType:['I_RREGULLT'],
    })
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(param => {
       this.currentPlayerId = param["id"]
    })
  }
  createContract(){
    const formValue = this.contractForm.value;
    let contractDto = new ContractDto(formValue.startDate,formValue.endDate,formValue.salary,formValue.contractType);
    this.contractService.createContract(this.currentPlayerId,contractDto).subscribe(res=>{
      this.router.navigate(["/players"])
    });
  }
}
