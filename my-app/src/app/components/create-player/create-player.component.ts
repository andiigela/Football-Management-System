import {Component, OnInit} from '@angular/core';
import {Foot} from "../../enums/foot";
import {FootballPosition} from "../../enums/football-position";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {PlayerDto} from "../../common/player-dto";
import {PlayerService} from "../../services/player.service";

@Component({
  selector: 'app-create-player',
  templateUrl: './create-player.component.html',
  styleUrls: ['./create-player.component.css']
})
export class CreatePlayerComponent implements OnInit {
  playerForm:FormGroup;
  footOptions = Object.values(Foot);
  positionOptions = Object.values(FootballPosition);
  constructor(private formBuilder: FormBuilder,private playerService: PlayerService) {
    this.playerForm = this.formBuilder.group({
      name: [''],
      height: [''],
      weight: [''],
      shirtNumber: [''],
      preferredFoot: [''],
      position: [''],
    })
  }
  ngOnInit(): void {
  }

  public createPlayer(): void {
    let name = this.playerForm.get("name")?.value
    let height = this.playerForm.get("height")?.value
    let weight = this.playerForm.get("weight")?.value
    let shirtNumber = this.playerForm.get("shirtNumber")?.value
    let preferredFoot = this.playerForm.get("preferredFoot")?.value
    let position = this.playerForm.get("position")?.value;
    let playerDto = new PlayerDto(name,height,weight,shirtNumber,preferredFoot,position);

    this.playerService.createPlayer(playerDto).subscribe(res => {
       console.log(res)
    })
  }
}
