import {Component, Injectable, OnInit} from '@angular/core';
import {PlayerService} from "../../services/player.service";
import {PlayerDto} from "../../common/player-dto";
import {Router} from "@angular/router";
import {PlayerResponseDto} from "../../common/player-response-dto";
@Component({
  selector: 'app-players-list',
  templateUrl: './players-list.component.html',
  styleUrls: ['./players-list.component.css']
})
export class PlayersListComponent implements OnInit{
  playersList: PlayerDto[]=[];
  pageNumber: number = 1;
  pageSize: number = 10;
  totalElements: number = 0;

  constructor(private playerService: PlayerService,private router: Router) {
  }
  ngOnInit(): void {
    this.getPlayers();
  }
  public getPlayers(){
    this.playerService.retrievePlayers(this.pageNumber-1,this.pageSize).subscribe((response)=>{
      this.playersList = response.content;
      console.log(this.playersList)
      this.totalElements = response.totalElements;
      this.updatePlayerList(this.playersList) // this.playersList, sepse kemi inicializu 2 rreshta me larte.
    })
  }
  OnPageChange(pageNumber: number){
    this.pageNumber = pageNumber;
    this.getPlayers();
  }
  editPlayer(id: number){
    this.router.navigate(['/players/edit/',id])
  }
  deletePlayer(id: number){
    this.playerService.deletePlayer(id)
        .subscribe(()=> {
          this.playersList = this.playersList.filter(player => player.id != id)
        })
  }
  updatePlayerList(playersList: PlayerDto[]){
    playersList.forEach(playerDto => this.getPlayerImageUrl(playerDto)) // set ImagePath to player.imagePath for each player
  }
  getPlayerImageUrl(playerDto: PlayerDto):void {
    if (playerDto.imagePath) {
      this.playerService.getImageUrl(playerDto.imagePath).subscribe((blob: Blob) => {
          const imageUrl = URL.createObjectURL(blob);
          playerDto.imagePath = imageUrl;
        },
        error => {
          console.error(`Error fetching image for player: ${playerDto.name}`, error);
          playerDto.imagePath = ''; // Set default image path
        }
      );
    }
  }
  redirectToPlayerInjuries(playerId: number){
    this.router.navigate([`/players/${playerId}/injuries`]);
  }
  redirectToCreatePlayerContract(playerId: number){
    this.router.navigate([`/players/${playerId}/contracts/create`])
  }
  redirectToPlayerContracts(playerId: number){
    this.router.navigate([`/players/${playerId}/contracts`])
  }
}
