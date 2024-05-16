import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {InjuryService} from "../../services/injury.service";
import {ActivatedRoute, Router} from "@angular/router";
import {InjuryStatus} from "../../enums/injury-status";
import {InjuryDto} from "../../common/injury-dto";
import {DateValidator} from "../../validators/date-validator";

@Component({
  selector: 'app-injury-edit',
  templateUrl: './injury-edit.component.html',
  styleUrl: './injury-edit.component.css'
})
export class InjuryEditComponent implements OnInit {
  injuryForm: FormGroup;
  injuryStatusList = Object.values(InjuryStatus)
  currentPlayerId = 0;
  currentInjuryId = 0;
  constructor(private injuryService: InjuryService, private formBuilder: FormBuilder,
              private activatedRoute: ActivatedRoute,private router: Router) {
    this.injuryForm = this.formBuilder.group({
      injuryType: ['',[Validators.required]],
      injuryDate: ['',[Validators.required,DateValidator.NotValidCreationDate]],
      expectedRecoveryTime: ['',[Validators.required,DateValidator.NotValidExpectedRecoveryDate]],
      injuryStatus: ['ACTIVE']
    })
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(param => {
     this.currentPlayerId = param["id"];
     this.currentInjuryId = param["injuryId"];
     this.getInjury(this.currentPlayerId,this.currentInjuryId)
    })
  }
  editInjury(){
    if(this.injuryForm.valid){
      const formData = this.injuryForm.value;
      const injuryDto = new InjuryDto(formData.injuryType, formData.injuryDate,
          formData.expectedRecoveryTime,
          formData.injuryStatus);
      injuryDto.id = this.currentInjuryId;
      this.injuryService.editInjury(this.currentPlayerId,injuryDto).subscribe(()=>{
        this.router.navigate([`/players/${this.currentPlayerId}/injuries`])
      })
    } else {
      this.injuryForm.markAllAsTouched();
    }

  }
  getInjury(playerId: number, injuryId: number){
    this.injuryService.retrieveInjury(playerId,injuryId).subscribe(injury => {
      this.injuryForm.patchValue({
        injuryType: injury.injuryType,
        injuryDate: injury.injuryDate,
        expectedRecoveryTime: injury.expectedRecoveryTime,
        injuryStatus: injury.injuryStatus
      });
    });
  }

}
