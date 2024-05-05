import {Component, OnInit} from '@angular/core';
import {InjuryService} from "../../services/injury.service";

@Component({
  selector: 'app-create-injury',
  imports: [],
  templateUrl: './create-injury.component.html',
  styleUrl: './create-injury.component.css'
})
export class CreateInjuryComponent implements OnInit{
  constructor(private injuryService: InjuryService) {
  }
  ngOnInit(): void {
  }

}
