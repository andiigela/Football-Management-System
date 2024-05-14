import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
                private router: Router,
                private seasonService: SeasonService,
                private roundsService: RoundsService) { } // Inject the RoundsService

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            const paramSeasonId = +params['seasonId'];
            if (!isNaN(paramSeasonId)) {
                this.seasonId = paramSeasonId;
                this.fetchRoundsForSeason(this.seasonId);
            } else {
                // Handle the case when seasonId is not provided or invalid
                console.error('Invalid or missing seasonId parameter');
            }
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
            if (round.id !== undefined) { // Check if round.id is defined
                this.roundsService.getMatchesForRound(round.id).subscribe(
                    matches => {
                        round.matches = matches;
                    },
                    error => {
                        console.error(`Error fetching matches for round ${round.id}:`, error);
                    }
                );
            } else {
                console.error('Round ID is undefined');
            }
        });
    }


    createRound(): void {
        if (this.seasonId) {
            this.router.navigate(['/league', 2, 'seasons', this.seasonId, 'create-round']);
        } else {
            console.error('Season ID is undefined or null');
        }
    }
}
