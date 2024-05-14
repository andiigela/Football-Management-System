import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SeasonService } from '../../services/season.service';
import { RoundDto } from '../../common/round-dto';
import { RoundsService } from '../../services/rounds.service';
import { MatchDto } from '../../common/match-dto'; // Import MatchDto

@Component({
    selector: 'app-rounds',
    templateUrl: './rounds.component.html',
    styleUrls: ['./rounds.component.css']
})
export class RoundsComponent implements OnInit {
    seasonId!: number;
    rounds: RoundDto[] = [];
    isCurrentSeason: boolean = false;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private seasonService: SeasonService,
                private roundsService: RoundsService) { }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            const paramSeasonId = +params['seasonId'];
            if (!isNaN(paramSeasonId)) {
                this.seasonId = paramSeasonId;
                this.fetchRoundsForSeason(this.seasonId);
                this.checkIfCurrentSeason(this.seasonId);
            } else {
                console.error('Invalid or missing seasonId parameter');
            }
        });
    }

    fetchRoundsForSeason(seasonId: number): void {
        this.seasonService.getRoundsForSeason(seasonId).subscribe(
            rounds => {
                this.rounds = rounds;
                this.loadMatchesForRounds();
            },
            error => {
                console.error('Error fetching rounds:', error);
            }
        );
    }

    loadMatchesForRounds(): void {
        this.rounds.forEach(round => {
            if (round.id !== undefined) {
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

    checkIfCurrentSeason(seasonId: number): void {
        this.seasonService.getSeasonById(seasonId).subscribe(
            season => {
                this.isCurrentSeason = season.currentSeason;
            },
            error => {
                console.error('Error fetching season:', error);
            }
        );
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
