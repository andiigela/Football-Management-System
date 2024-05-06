import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {InjuryService} from "../../services/injury.service";
import {ActivatedRoute} from "@angular/router";
import {InjuryStatus} from "../../enums/injury-status";

@Component({
  selector: 'app-injury-edit',
  templateUrl: './injury-edit.component.html',
  styleUrl: './injury-edit.component.css'
})
export class InjuryEditComponent implements OnInit {
  injuryForm: FormGroup;
  injuryStatusList = Object.values(InjuryStatus);
  constructor(private injuryService: InjuryService, private formBuilder: FormBuilder,private activatedRoute: ActivatedRoute) {
    this.injuryForm = formBuilder.group({
      injuryType: [''],
      injuryDate: [''],
      expectedRecoveryTime: [''],
      injuryStatus: ['ACTIVE']
    })
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(param => {
      // this.currentPlayerId = param["id"]
      // this.currentPlayerInjuryId = param["injuryId"]
    })
  }

}
