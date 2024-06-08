import {Component, OnInit} from '@angular/core';
import {TransferDto} from "../../common/transfer-dto";
import {FormsModule} from "@angular/forms";
import {PlayerService} from "../../services/player.service";
import {NgForOf, NgIf} from "@angular/common";
import {PlayerDto} from "../../common/player-dto";
import {ClubService} from "../../services/club.service";
import {ClubDto} from "../../common/club-dto";
import {TransferService} from "../../services/transfer.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-make-transfer',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './make-transfer.component.html',
  styleUrl: './make-transfer.component.css'
})
export class MakeTransferComponent implements OnInit{
  transfer: TransferDto = new TransferDto(0, {} as PlayerDto, {} as ClubDto, {} as ClubDto, new Date(), 0);
  players: PlayerDto[] = [];
  clubs : ClubDto[]=[];
  selectedPlayer: PlayerDto | null = null;


  constructor(private router :Router,private playerService: PlayerService , private clubService : ClubService,private transferService:TransferService) { }

  ngOnInit() {
    this.fetchPlayers();
    this.fetchTeams()
  }

  fetchPlayers() {
    this.playerService.getAllPLayers().subscribe(
      (data: any[]) => {
        console.log(data)
        this.players = data;
      },
      (error) => {
        console.error('Error fetching players:', error);
      }
    );
  }
  fetchTeams() {
    this.clubService.getAllClubs().subscribe(
      (data: any[]) => {
        this.clubs = data;

      },
      (error) => {
        console.error('Error fetching players:', error);
      }
    );
  }
  onPlayerSelected() {
    console.log(this.selectedPlayer)
    const playerId = this.selectedPlayer?.id;
    if (playerId !== undefined) {
      const player = this.players.find(p => p.id === playerId);
      if (player) {
        this.selectedPlayer = player;
        this.transfer.player = player;
        this.transfer.previousClub = player.club!;
      } else {
        this.selectedPlayer = null;
        this.transfer.previousClub = {} as ClubDto;
      }
    }
  }

  transferPlayer() {
    if (this.selectedPlayer) {
      this.transfer.player = this.selectedPlayer;
      this.transfer.transferDate = new Date();
      this.transferService.createTransfer(this.transfer).subscribe(response => {
        this.router.navigateByUrl("/dashboard")
      });
    }
  }
}
