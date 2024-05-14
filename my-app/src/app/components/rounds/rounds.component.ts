import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SeasonService } from '../../services/season.service';
import { RoundDto } from '../../common/round-dto';
import { MatchDto } from '../../common/match-dto';
import { RoundsService } from '../../services/rounds.service'; // Import the RoundsService

@Component({
    selector: 'app-rounds',
    templateUrl: './rounds.component.html',
    styleUrls: ['./rounds.component.css']
})
export class RoundsComponent implements OnInit {
    seasonId!: number;
    rounds: RoundDto[] = [];

    constructor(private route: ActivatedRoute,
                private seasonService: SeasonService,
                private roundsService: RoundsService) { } // Inject the RoundsService

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.seasonId = +params['seasonId'];
            this.fetchRoundsForSeason(this.seasonId);
        });
    }

    fetchRoundsForSeason(seasonId: number): void {
        this.seasonService.getRoundsForSeason(seasonId).subscribe(
            rounds => {
                this.rounds = rounds;
                this.loadMatchesForRounds(); // Call function to load matches for each round
            },
            error => {
                console.error('Error fetching rounds:', error);
            }
        );
    }

    loadMatchesForRounds(): void {
        this.rounds.forEach(round => {
            this.roundsService.getMatchesForRound(round.id).subscribe(
                matches => {
                    round.matches = matches;
                    console.log(matches);
                },
                error => {
                    console.error(`Error fetching matches for round ${round.id}:`, error);
                }
            );
        });
    }
}
