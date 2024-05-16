import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatchService } from '../../services/match.service';
import { MatchDto } from '../../common/match-dto';

@Component({
    selector: 'app-match',
    templateUrl: './match.component.html',
    styleUrls: ['./match.component.css']
})
export class MatchComponent implements OnInit {

    id!: number;
    match!: MatchDto;
    leagueId!: number;
    roundId!: number;
    seasonId!: number;

    constructor(private route: ActivatedRoute, private router: Router, private matchService: MatchService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['matchId'];
      this.leagueId = +params['leagueId'];
      this.seasonId = +params['seasonId'];
      this.roundId = +params['roundId'];
      this.loadMatchDetails(this.roundId, this.id);
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

  editMatch(): void {
        this.matchService.editMatch(this.roundId, this.id, this.match).subscribe(
            () => {
                console.log('Match updated successfully');
                this.router.navigate(['/league', this.leagueId, 'seasons', this.seasonId, 'rounds']);
            },
            error => {
                console.error('Error updating match:', error);
            }
        );
    }


    formatMatchDate(date: Date): string {
        const options: Intl.DateTimeFormatOptions = {
            year: 'numeric',
            month: 'short',
            day: '2-digit',
            hour: 'numeric',
            minute: 'numeric'
        };
        return date.toLocaleString('en-US', options);
    }
}
