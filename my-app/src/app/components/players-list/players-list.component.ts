import { Component, OnInit } from '@angular/core';
import { PlayerService } from '../../services/player.service';
import { ContractService } from '../../services/contract.service'; // Import ContractService
import { PlayerDto } from '../../common/player-dto';
import { Router } from '@angular/router';
import { ContractDto } from '../../common/contract-dto';

@Component({
  selector: 'app-players-list',
  templateUrl: './players-list.component.html',
  styleUrls: ['./players-list.component.css']
})
export class PlayersListComponent implements OnInit {
  playersList: PlayerDto[] = [];
  pageNumber: number = 1;
  pageSize: number = 5;
  totalElements: number = 0;
  sortedByHeightAsc: boolean = false;
  sortedByWeightAsc: boolean = false;

  constructor(private playerService: PlayerService, private contractService: ContractService, private router: Router) {}

  ngOnInit(): void {
    this.getPlayers();
  }

  public getPlayers(): void {
    this.playerService.retrievePlayers(this.pageNumber - 1, this.pageSize).subscribe((response) => {
      this.playersList = response.content;
      this.totalElements = response.totalElements;
      this.updatePlayerList(this.playersList);
      this.fetchContractsForPlayers(this.playersList); // Fetch contracts after players are loaded
    });
  }

  OnPageChange(pageNumber: number): void {
    this.pageNumber = pageNumber;
    this.getPlayers();
  }

  editPlayer(id: number): void {
    this.router.navigate(['/players/edit/', id]);
  }

  deletePlayer(id: number): void {
    this.playerService.deletePlayer(id).subscribe(() => {
      this.playersList = this.playersList.filter(player => player.dbId !== id);
    });
  }

  updatePlayerList(playersList: PlayerDto[]): void {
    playersList.forEach(playerDto => this.getPlayerImageUrl(playerDto));
  }

  getPlayerImageUrl(playerDto: PlayerDto): void {
    if (playerDto.imagePath) {
      this.playerService.getImageUrl(playerDto.imagePath).subscribe((blob: Blob) => {
            const imageUrl = URL.createObjectURL(blob);
            playerDto.imagePath = imageUrl;
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
