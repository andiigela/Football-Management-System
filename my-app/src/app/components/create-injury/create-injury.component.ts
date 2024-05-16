import {Component, OnInit} from '@angular/core';
import {InjuryService} from "../../services/injury.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {InjuryStatus} from "../../enums/injury-status";
import {PlayerDto} from "../../common/player-dto";
import {InjuryDto} from "../../common/injury-dto";
import {ActivatedRoute, Router} from "@angular/router";
import {DateValidator} from "../../validators/date-validator";

@Component({
  selector: 'app-create-injury',
  templateUrl: './create-injury.component.html',
  styleUrl: './create-injury.component.css'
})
export class CreateInjuryComponent implements OnInit{
  injuryForm: FormGroup;
  injuryStatusList = Object.values(InjuryStatus);
  currentPlayerId: number = 0;
  constructor(private injuryService: InjuryService,private formBuilder: FormBuilder,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
    this.injuryForm = this.formBuilder.group({
      injuryType: ['',[Validators.required]],
      injuryDate: ['',[Validators.required,DateValidator.NotValidCreationDate]],
      expectedRecoveryTime: ['',[Validators.required,DateValidator.NotValidExpectedRecoveryDate]],
      injuryStatus: ['ACTIVE'],
    })
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.currentPlayerId = +params["id"]
    })
  }
  createInjury(){
    if(this.injuryForm.valid){
      const formData = this.injuryForm.value;
      const injuryDto = new InjuryDto(
          formData.injuryType,
          formData.injuryDate,
          formData.expectedRecoveryTime,
          formData.injuryStatus);
      this.injuryService.createInjury(this.currentPlayerId,injuryDto).subscribe(()=>{
        this.router.navigateByUrl(`/players/${this.currentPlayerId}/injuries`)
      });
    } else {
      this.injuryForm.markAllAsTouched();
    }
  }

}
