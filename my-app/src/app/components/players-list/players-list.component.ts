import {Component, OnDestroy, OnInit} from '@angular/core';
import {PlayerService} from "../../services/player.service";
import {PlayerDto} from "../../common/player-dto";
import {Router} from "@angular/router";
import {WebSocketService} from "../../services/web-socket.service";
import {PlayerIdDto} from "../../common/player-id-dto";
import {AuthService} from "../../services/auth.service";
import { ContractService } from '../../services/contract.service'; // Import ContractService
import { ContractDto } from '../../common/contract-dto';

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
  sortedByHeightAsc: boolean = false;
  sortedByWeightAsc: boolean = false;
  private connectionId1: string = "playeraskedpermission";
  private connectionId2: string = "deletedplayer";
  playersWhoAskedPermission: PlayerIdDto[]=[];
  playerWhoAskedPermission: PlayerIdDto|null=null;
  constructor(private playerService: PlayerService,private router: Router,
              private webSocketService: WebSocketService, private contractService: ContractService,
              private authService: AuthService) {
  }
  ngOnDestroy(): void {
    this.webSocketService.disconnect(this.connectionId1);
    this.webSocketService.disconnect(this.connectionId2);
  }

  ngOnInit(): void {
    this.webSocketService.connect(("/topic/askedpermission/"+this.authService.getUserIdFromToken()),this.connectionId1);
    this.webSocketService.connect(("/topic/playerDeleted/"+this.authService.getUserIdFromToken()),this.connectionId2);
    this.getPlayers();
    this.subscribeToDeletedPlayersFromBroker();

  }
  private subscribeToRetrievedAskedPermissionPlayersFromApi(){
    this.playerService.getPlayerIdsWhoAskedPermissionFromCurrentUser().subscribe((playerIds: PlayerIdDto[])=>{
      this.playersWhoAskedPermission=playerIds;
      this.updatePlayersListWithNotifications();
    })
  }
  private subscribeToSentNotificationsFromBroker(): void {
    this.webSocketService.getMessages(this.connectionId1).subscribe((data: any[]) => {
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
  private subscribeToDeletedPlayersFromBroker(){
      this.webSocketService.getMessages(this.connectionId2).subscribe((data: any)=>{
          const deletedPlayerId: number = JSON.parse(data);
          this.playersList = this.playersList.filter(player => player.dbId != deletedPlayerId);
          this.totalElements--;
          if(this.totalElements <= 0){
            this.getPlayers();
          }
          console.log("player deleted: " + deletedPlayerId);
      })
  }
  private updatePlayersListWithNotifications(): void {
    this.playersList = this.playersList.map((playerDto: PlayerDto) => {
      playerDto.permissionSent = this.playersWhoAskedPermission
          .some((playerIdDto: PlayerIdDto) => playerIdDto.id === playerDto.dbId
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
      //  this.fetchContractsForPlayers(this.playersList); Fetch contracts after players are loaded
    })
  }

  OnPageChange(pageNumber: number): void {
    this.pageNumber = pageNumber;
    this.getPlayers();
  }

  editPlayer(id: number): void {
    this.router.navigate(['/players/edit/', id]);
  }

  deletePlayer(id: number){
    this.playerService.sendDeletePlayerPermission(id)
        .subscribe(()=> {
          this.subscribeToSentNotificationsFromBroker();
        })
  }

  updatePlayerList(playersList: PlayerDto[]): void {
    playersList.forEach(playerDto => this.getPlayerImageUrl(playerDto));
  }

  getPlayerImageUrl(playerDto: PlayerDto): void {
    if (playerDto.imagePath) {
      this.playerService.getImageUrl(playerDto.imagePath).subscribe((blob: Blob) => {
            const imageUrl = URL.createObjectURL(blob);
            playerDto.imagePath = imageUrl;
            console.log(imageUrl);
          },
          error => {
            console.error(`Error fetching image for player: ${playerDto.name}`, error);
            playerDto.imagePath = ''; // Set default image path
          });
    }
  }

  redirectToPlayerInjuries(playerId: number): void {
    this.router.navigate([`/players/${playerId}/injuries`]);
  }

  redirectToCreatePlayerContract(playerId: number): void {
    this.router.navigate([`/players/${playerId}/contracts/create`]);
  }

  redirectToPlayerContracts(playerId: number): void {
    this.router.navigate([`/players/${playerId}/contracts`]);
  }

  sortByHeight(): void {
    this.sortedByHeightAsc = !this.sortedByHeightAsc;
    if (this.sortedByHeightAsc) {
      this.playerService.getPlayersSortedByHeight(this.pageNumber - 1, this.pageSize).subscribe(players => {
        this.playersList = players.content;
        this.updatePlayerList(this.playersList); // Call updatePlayerList after sorting
        this.fetchContractsForPlayers(this.playersList); // Fetch contracts after sorting
      });
    } else {
      this.playerService.getPlayersSortedByHeightDesc(this.pageNumber - 1, this.pageSize).subscribe(players => {
        this.playersList = players.content;
        this.updatePlayerList(this.playersList); // Call updatePlayerList after sorting
        this.fetchContractsForPlayers(this.playersList); // Fetch contracts after sorting
      });
    }
  }

  sortByWeight(): void {
    this.sortedByWeightAsc = !this.sortedByWeightAsc;
    if (this.sortedByWeightAsc) {
      this.playerService.getPlayersSortedByWeight(this.pageNumber - 1, this.pageSize).subscribe(players => {
        this.playersList = players.content;
        this.updatePlayerList(this.playersList); // Call updatePlayerList after sorting
        this.fetchContractsForPlayers(this.playersList); // Fetch contracts after sorting
      });
    } else {
      this.playerService.getPlayersSortedByWeightDesc(this.pageNumber - 1, this.pageSize).subscribe(players => {
        this.playersList = players.content;
        this.updatePlayerList(this.playersList); // Call updatePlayerList after sorting
        this.fetchContractsForPlayers(this.playersList); // Fetch contracts after sorting
      });
    }
  }

  fetchContractsForPlayers(playersList: PlayerDto[]): void {
    playersList.forEach(player => {
      this.contractService.retrieveContracts(player.dbId, 0, 10).subscribe(response => {
        player.contracts = response.content;
      });
    });
  }
}
