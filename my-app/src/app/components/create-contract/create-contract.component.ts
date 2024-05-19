import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ContractDto} from "../../common/contract-dto";
import {ActivatedRoute, Router} from "@angular/router";
import {ContractService} from "../../services/contract.service";
import {ContractType} from "../../enums/contract-type";
import {DateValidator} from "../../validators/date-validator";

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
      startDate:['',[Validators.required,DateValidator.NotValidCreationDate]],
      endDate:['',[Validators.required,DateValidator.NotValidExpectedRecoveryDate]],
      salary:[0,[Validators.required]],
      contractType:['I_RREGULLT'],
    })
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(param => {
       this.currentPlayerId = param["id"]
    })
  }
  createContract(){
    if(this.contractForm.valid){
      const formValue = this.contractForm.value;
      let contractDto = new ContractDto(formValue.startDate,formValue.endDate,formValue.salary,formValue.contractType);
      this.contractService.createContract(this.currentPlayerId,contractDto).subscribe(res=>{
        this.router.navigate(["/players"])
      });
    } else {
      this.contractForm.markAllAsTouched();
    }
  }
}
