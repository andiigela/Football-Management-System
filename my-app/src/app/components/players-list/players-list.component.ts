import {Component, OnInit} from '@angular/core';
import {PlayerService} from "../../services/player.service";
import {PlayerDto} from "../../common/player-dto";
import {Router} from "@angular/router";
import {PageResponseDto} from "../../common/page-response-dto";

@Component({
  selector: 'app-players-list',
  templateUrl: './players-list.component.html',
  styleUrls: ['./players-list.component.css']
})
export class PlayersListComponent implements OnInit{
  playersList: PageResponseDto = new PageResponseDto([],0,0,0);
  constructor(private playerService: PlayerService,private router: Router) {
  }
  ngOnInit(): void {
    this.getPlayers();
  }
  public getPlayers(){
    this.playerService.retrievePlayers("0","10").subscribe((response)=>{
       this.playersList = response;
       console.log(response);
    })
  }
  editPlayer(id: number){
    this.router.navigate(['/players/edit/',id])
  }
  deletePlayer(id: number){
    this.playerService.deletePlayer(id)
        .subscribe(()=> {
          //this.playersList = this.playersList.filter(player => player.id != id)
        })
  }
}
