import {Component, Injectable, OnInit} from '@angular/core';
import {PlayerService} from "../../services/player.service";
import {PlayerDto} from "../../common/player-dto";
import {Router} from "@angular/router";
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
      response.content.forEach(player => {
        this.getPlayerImageUrl(player)
      })
      this.playersList = response.content;
      this.totalElements = response.totalElements;
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
          this.getPlayers();
        })
  }
  getPlayerImageUrl(playerDto: PlayerDto):void {
    if(playerDto.imagePath != ""){
      this.playerService.getImageUrl(playerDto.imagePath).subscribe(()=>{
        playerDto.imagePath = `http://localhost:8080/images/${playerDto.imagePath}`
      },(err)=>{
        console.log(err);
        playerDto.imagePath = '';
      })
    }
  }
}
