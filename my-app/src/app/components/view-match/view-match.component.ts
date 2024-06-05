import {Component, OnInit} from '@angular/core';
import {MatchDto} from "../../common/match-dto";
import {ActivatedRoute, Router} from "@angular/router";
import {MatchService} from "../../services/match.service";
import {PlayerService} from "../../services/player.service";
import {PlayerDto} from "../../common/player-dto";
import {NgForOf, NgIf} from "@angular/common";
import {Matcheventdto} from "../../common/matcheventdto";

@Component({
  selector: 'app-view-match',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './view-match.component.html',
  styleUrl: './view-match.component.css'
})
export class ViewMatchComponent implements OnInit{

  id!: number;
  match!: MatchDto;
  leagueId!: number;
  roundId!: number;
  seasonId!: number;
  roundEvents: Matcheventdto[]=[];
  arrayOfEvents : Matcheventdto[]=[];



  constructor(private route: ActivatedRoute, private router: Router, private matchService: MatchService) { }


  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['matchId'];
      this.leagueId = +params['leagueId'];
      this.seasonId = +params['seasonId'];
      this.roundId = +params['roundId'];
      this.loadMatchDetails(this.roundId, this.id);
      this.matchService.getMatchEvents(this.roundId,this.id).subscribe(data=>{
        this.roundEvents=data




      })
    });
  }

  loadMatchDetails(roundId: number, matchId: number): void {
    this.matchService.getMatch(roundId, matchId).subscribe(
      match => {
        this.match = match;

      },
      error => {
        console.error('Error fetching match:', error);
      }
    );
  }


  formatMatchDate(date: Date): string {
    const options: Intl.DateTimeFormatOptions = {
      hour: 'numeric',
      minute: 'numeric'
    };
    return new Date(date).toLocaleString('en-US', options);
  }

  goToCreateEvent(roundId: number, matchId: number) {
    this.router.navigate([`league/${this.leagueId}/seasons/${this.seasonId}/rounds/${roundId}/match/${matchId}/create`])
  }
}
