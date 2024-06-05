import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SeasonService } from '../../services/season.service';
import { RoundDto } from '../../common/round-dto';
import { RoundsService } from '../../services/rounds.service';
import { MatchDto } from '../../common/match-dto';
import { MatchService } from '../../services/match.service';
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-rounds',
  templateUrl: './rounds.component.html',
  styleUrls: ['./rounds.component.css']
})
export class RoundsComponent implements OnInit {
  seasonId!: number;
  rounds: RoundDto[] = [];
  filteredMatches: MatchDto[] = [];
  isCurrentSeason: boolean = false;
  leagueId: number | null = null;
  pageNumber: number = 1;
  pageSize: number = 3;
  totalElements: number = 0;
  private dateFormat: string = 'yyyy-MM-ddTHH:mm:ss';
  date!: string;
  homeTeamResult!: number;
  awayTeamResult!: number;
  isFilterApplied: boolean = false;
  startDate!:string;
  endDate!: string;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private seasonService: SeasonService,
    private roundsService: RoundsService,
    private matchService: MatchService,
    private datePipe: DatePipe
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


  redirectToEditMatch(roundId: number | undefined, matchId: number): void {
    if (roundId !== undefined) {
      this.router.navigate(['/league', this.leagueId, 'seasons', this.seasonId, 'rounds', roundId, 'edit-match', matchId]);
    } else {
      console.error('Round ID is undefined');
    }
  }

  hasMatchStarted(matchDate: Date): boolean {
    const now = new Date();
    const matchStartDate = new Date(matchDate);
    const matchEndDate = new Date(matchDate);
    matchEndDate.setMinutes(matchEndDate.getMinutes() + 90); // Add 90 minutes to match start time

    return now >= matchStartDate && now <= matchEndDate;
  }

  goToMatch(roundId: number|undefined , matchId: number) {
    this.router.navigate([`league/${this.leagueId}/seasons/${this.seasonId}/rounds/${roundId}/match/${matchId}`])
  }
  private formatDate(date: string): string {
    const formattedDate = new Date(date);
    return this.datePipe.transform(formattedDate, this.dateFormat) || '';
  }
  filterMatches(): void {
    const formattedDate = this.formatDate(this.date);
    this.matchService.filterMatches(formattedDate, this.homeTeamResult, this.awayTeamResult, this.pageNumber-1, this.pageSize).subscribe(
      response => {
        this.filteredMatches = response.content;
        this.isFilterApplied = true;
        console.log('Filtered matches:', this.filteredMatches);
      },
      error => {
        console.error('Error filtering matches:', error);
      }
    );
  }

  clearFilters(): void {
    this.filteredMatches = [];
    this.isFilterApplied = false;
    this.fetchRoundsForSeason();
  }
  filterRoundsByDateRange(): void {
    if (this.startDate && this.endDate) {
      // Append seconds to the dates if not already present
      if (!this.startDate.endsWith(':00')) {
        this.startDate += ':00';
      }
      if (!this.endDate.endsWith(':00')) {
        this.endDate += ':00';
      }

      this.roundsService.filterRoundsByDateRange(this.startDate, this.endDate, this.pageNumber - 1, this.pageSize).subscribe(
          response => {
            const filteredRounds = response.content;
            this.totalElements = response.totalElements;
            console.log(filteredRounds);
            // Filter rounds to include only those containing the filtered dates
            this.rounds = this.rounds.filter(round => {
              const roundStartDate = new Date(round.start_date);
              const roundEndDate = new Date(round.end_date);
              const filterStartDate = new Date(this.startDate);
              const filterEndDate = new Date(this.endDate);

              // Check if round's start or end date falls within the filtered date range
              return (roundStartDate >= filterStartDate && roundStartDate <= filterEndDate) ||
                  (roundEndDate >= filterStartDate && roundEndDate <= filterEndDate);
            });

            this.isFilterApplied = true;
            console.log('Filtered rounds:', this.rounds);
          },
          error => {
            console.error('Error filtering rounds:', error);
          }
      );
    } else {
      console.error('Start date or end date is not selected.');
    }
  }





}
