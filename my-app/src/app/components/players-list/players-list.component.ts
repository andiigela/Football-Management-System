import {Component, OnInit} from '@angular/core';
import {PlayerService} from "../../services/player.service";
import {PlayerDto} from "../../common/player-dto";

@Component({
  selector: 'app-players-list',
  templateUrl: './players-list.component.html',
  styleUrls: ['./players-list.component.css']
})
export class PlayersListComponent implements OnInit{
  playersList: PlayerDto[] = [];
  constructor(private playerService: PlayerService) {
  }

  ngOnInit(): void {
    this.getPlayers();
  }
  public getPlayers(){
    this.playerService.retrievePlayers().subscribe((response)=>{
       this.playersList = response;
    })
  }

}
