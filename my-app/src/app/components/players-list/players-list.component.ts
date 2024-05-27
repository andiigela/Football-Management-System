import {Component, OnDestroy, OnInit} from '@angular/core';
import {PlayerService} from "../../services/player.service";
import {PlayerDto} from "../../common/player-dto";
import {Router} from "@angular/router";
import {WebSocketService} from "../../services/web-socket.service";
import {PlayerIdDto} from "../../common/player-id-dto";
import {AuthService} from "../../services/auth.service";
@Component({
  selector: 'app-players-list',
  templateUrl: './players-list.component.html',
  styleUrls: ['./players-list.component.css']
})
export class PlayersListComponent implements OnInit, OnDestroy{
  playersList: PlayerDto[]=[];
  pageNumber: number = 1;
  pageSize: number = 10;
  totalElements: number = 0;
  private connectionId: string = "playeraskedpermission";
  playersWhoAskedPermission: PlayerIdDto[]=[];
  playerWhoAskedPermission: PlayerIdDto|null=null;

  constructor(private playerService: PlayerService,private router: Router,
              private webSocketService: WebSocketService,
              private authService: AuthService) {
  }
  ngOnDestroy(): void {
    this.webSocketService.disconnect(this.connectionId);
  }
  ngOnInit(): void {
    this.webSocketService.connect(("/topic/askedpermission/"+this.authService.getUserIdFromToken()),this.connectionId);
    this.getPlayers();
  }
  private subscribeToRetrievedAskedPermissionPlayersFromApi(){
    this.playerService.getPlayerIdsWhoAskedPermissionFromCurrentUser().subscribe((playerIds: PlayerIdDto[])=>{
      this.playersWhoAskedPermission=playerIds;
      this.updatePlayersListWithNotifications();
    })
  }
  private subscribeToSentNotifications(): void {
    this.webSocketService.getMessages(this.connectionId).subscribe((data: any[]) => {
      if(data.length > 0){
        if (typeof data === 'string') {
          this.playerWhoAskedPermission = JSON.parse(data);
          this.playersWhoAskedPermission.push(this.playerWhoAskedPermission!);
          console.log("Parsed notification: ", this.playerWhoAskedPermission);
          this.updatePlayersListWithNotifications();
        }
      }
    });
  }
  private updatePlayersListWithNotifications(): void {
    this.playersList = this.playersList.map((playerDto: PlayerDto) => {
      playerDto.permissionSent = this.playersWhoAskedPermission
          .some((playerIdDto: PlayerIdDto) => playerIdDto.id === playerDto.id
          );
      return playerDto;
    });
  }
  public getPlayers(){
    this.playerService.retrievePlayers(this.pageNumber-1,this.pageSize).subscribe((response)=>{
      this.playersList = response.content;
      this.totalElements = response.totalElements;
      this.updatePlayerList(this.playersList) // this.playersList, sepse kemi inicializu 2 rreshta me larte.
      this.subscribeToRetrievedAskedPermissionPlayersFromApi();
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
    this.playerService.sendDeletePlayerPermission(id)
        .subscribe(()=> {
          this.subscribeToSentNotifications();
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
