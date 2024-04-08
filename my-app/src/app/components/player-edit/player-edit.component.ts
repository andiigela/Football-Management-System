import {Component, OnInit} from '@angular/core';
import {PlayerService} from "../../services/player.service";
import {PlayerDto} from "../../common/player-dto";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Foot} from "../../enums/foot";
import {FootballPosition} from "../../enums/football-position";

@Component({
  selector: 'app-player-edit',
  templateUrl: './player-edit.component.html',
  styleUrls: ['./player-edit.component.css']
})
export class PlayerEditComponent implements OnInit{
  playerEdit: PlayerDto = new PlayerDto("",0,0,0,"","");
  editForm: FormGroup;
  footOptions = Object.values(Foot);
  positionOptions = Object.values(FootballPosition);
  constructor(private playerService: PlayerService,private route: ActivatedRoute,private formBuilder: FormBuilder) {
    this.editForm = formBuilder.group({
      name: [''],
      height: [''],
      weight: [''],
      shirtNumber: [''],
      preferredFoot: ['LEFT'],
      position: ['GOALKEEPER'],
    })
  }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const playerId = params["id"]
      this.getPlayer(playerId);
    })
  }
  getPlayer(playerId: number){
    this.playerService.retrievePlayer(playerId).subscribe((player)=>{
      this.playerEdit = player;
      console.log(this.playerEdit.preferred_foot)
      this.editForm.patchValue({
        name: player.name,
        height: player.height,
        weight: player.weight,
        shirtNumber: player.shirtNumber,
        preferredFoot: player.preferred_foot,
        position: 'GOALKEEPER'
      })
    })
  }

}
