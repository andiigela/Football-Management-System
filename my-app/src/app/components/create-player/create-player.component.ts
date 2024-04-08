import {Component, OnInit} from '@angular/core';
import {Foot} from "../../enums/foot";
import {FootballPosition} from "../../enums/football-position";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {PlayerDto} from "../../common/player-dto";
import {PlayerService} from "../../services/player.service";
import {Route, Router} from "@angular/router";
import {RegisterDto} from "../../common/register-dto";

@Component({
  selector: 'app-create-player',
  templateUrl: './create-player.component.html',
  styleUrls: ['./create-player.component.css']
})
export class CreatePlayerComponent implements OnInit {
  playerForm:FormGroup;
  footOptions = Object.values(Foot);
  positionOptions = Object.values(FootballPosition);
  constructor(private formBuilder: FormBuilder,private playerService: PlayerService,private router: Router) {
    this.playerForm = this.formBuilder.group({
      name: [''],
      height: [''],
      weight: [''],
      shirtNumber: [''],
      preferredFoot: ['LEFT'],
      position: ['GOALKEEPER'],
    })
  }
  ngOnInit(): void {
  }

  public createPlayer(): void {
    const formData = this.playerForm.value;
    const playerDto = new PlayerDto(
      formData.name,
      formData.height,
      formData.weight,
      formData.shirtNumber,
      formData.preferredFoot,
      formData.position
    );
    console.log(playerDto)
    this.playerService.createPlayer(playerDto).subscribe(res=>{
           console.log(res)});
    this.router.navigateByUrl("/dashboard")

  }
}
