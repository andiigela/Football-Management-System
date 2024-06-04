// src/app/player-scouting/PlayerScouted-dto.ts
export interface Player {
  id: number;
  name: string;
  position: string;
  // Add other player fields as needed
}

export interface Injuries {
  id: number;
  description: string;
  date: string;
  // Add other injury fields as needed
}

export interface PlayerScouted {
  id: number;
  player: Player;
  injuries: Injuries[];
  report: string;
  totalInjuries: number;
}
