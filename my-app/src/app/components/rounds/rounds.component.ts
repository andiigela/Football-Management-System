import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SeasonService } from '../../services/season.service';
import { RoundDto } from '../../common/round-dto';
import { RoundsService } from '../../services/rounds.service';

@Component({
  selector: 'app-rounds',
  templateUrl: './rounds.component.html',
  styleUrls: ['./rounds.component.css']
})
export class RoundsComponent implements OnInit {
  seasonId!: number;
  rounds: RoundDto[] = [];
  isCurrentSeason: boolean = false;
  leagueId: number | null = null;
  pageNumber: number = 1;
  pageSize: number = 3;
  totalElements: number = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private seasonService: SeasonService,
    private roundsService: RoundsService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.leagueId = +params['leagueId'];
      this.seasonId = +params['seasonId'];
      this.fetchRoundsForSeason();
      this.checkIfCurrentSeason(this.seasonId);
    });
  }

  fetchRoundsForSeason(): void {
    console.log('Fetching rounds for page number:', this.pageNumber);
    this.roundsService.getRounds(this.seasonId, this.pageNumber-1, this.pageSize).subscribe(
      response => {
        this.rounds = response.content;
        this.totalElements = response.totalElements;
        console.log('Page number:', this.pageNumber);
        console.log('Total elements: ', this.totalElements);
        console.log('Page size:', this.pageSize);
        console.log('Rounds:', this.rounds);
      },
      error => {
        console.error('Error fetching rounds:', error);
      }
    );
  }

  onPageChange(pageNumber: number): void {
    console.log('Page change to:', pageNumber);
    this.pageNumber = pageNumber;
    this.fetchRoundsForSeason();

  }

  checkIfCurrentSeason(seasonId: number): void {
    if (this.leagueId !== null) {
      this.seasonService.getSeason(this.leagueId, seasonId).subscribe(
        season => {
          this.isCurrentSeason = season.currentSeason;
          console.log('Is current season:', this.isCurrentSeason);
        },
        error => {
          console.error('Error fetching season:', error);
        }
      );
    } else {
      console.error('League ID is null');
    }
  }

  createRound(): void {
    if (this.seasonId) {
      this.router.navigate(['/league', 2, 'seasons', this.seasonId, 'create-round']);
    } else {
      console.error('Season ID is undefined or null');
    }
  }

  redirectToEditMatch(roundId: number | undefined, matchId: number): void {
    if (roundId !== undefined) {
      this.router.navigate(['/league', 2, 'seasons', this.seasonId, 'rounds', roundId, 'edit-match', matchId]);
    } else {
      console.error('Round ID is undefined');
    }
  }
}
