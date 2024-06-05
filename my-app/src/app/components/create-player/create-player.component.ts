import {Component, OnInit} from '@angular/core';
import {Foot} from "../../enums/foot";
import {FootballPosition} from "../../enums/football-position";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {PlayerDto} from "../../common/player-dto";
import {PlayerService} from "../../services/player.service";
import {Route, Router} from "@angular/router";

@Component({
  selector: 'app-create-player',
  templateUrl: './create-player.component.html',
  styleUrls: ['./create-player.component.css']
})
export class CreatePlayerComponent implements OnInit {
  playerForm:FormGroup;
  footOptions = Object.values(Foot);
  positionOptions = Object.values(FootballPosition);
  file: File|null = null;
  constructor(private formBuilder: FormBuilder,private playerService: PlayerService,private router: Router) {
    this.playerForm = this.formBuilder.group({
      name: ['',[Validators.required,Validators.pattern('^[a-z A-Z]+$')]],
      height: ['',[Validators.required]],
      weight: ['',[Validators.required]],
      shirtNumber: ['',[Validators.required]],
      preferred_foot: ['LEFT'],
      position: ['GOALKEEPER'],
      file: [null]
    })
  }
  ngOnInit(): void {
  }
  public createPlayer(): void {
    if(this.playerForm.valid){
      const formData = this.playerForm.value;
      const playerDto = new PlayerDto(
        formData.name,
        formData.height,
        formData.weight,
        formData.shirtNumber,
        formData.preferred_foot,
        formData.position,
        formData.clubId
      );

        this.playerService.createPlayer(playerDto,this.file!).subscribe(()=>{
          this.router.navigateByUrl("/dashboard")
        },(err)=>{console.log(err)});

    } else {
      this.playerForm.markAllAsTouched();
    }
  }
  onFileSelected(event: any){
    const file: File = event.target.files[0];
    this.file = file;
  }
}
