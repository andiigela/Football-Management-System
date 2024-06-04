// src/app/player-scouting/player-scouted.component.ts
import { Component, OnInit } from '@angular/core';
import { PlayerScoutedService } from '../player-scouted.service'; // Corrected import path
import { PlayerScouted } from '../PlayerScoutedc.dto'; // Ensure proper path for import

@Component({
  selector: 'app-PlayerScouted',
  templateUrl: './PlayerScouted.component.ts',


})
export class PlayerScouted.Component implements OnInit {

  playersScouted: PlayerScouted[] = [];

  constructor(private playerScoutedService: PlayerScoutedService) {}

  ngOnInit(): void {
    this.playerScoutedService.getPlayerScouted().subscribe(
      (data: PlayerScouted[]) => {
        this.playersScouted = data;
      },
      (error: any) => {
        console.error('Error fetching player scouted data', error);
      }
    );
  }
}
