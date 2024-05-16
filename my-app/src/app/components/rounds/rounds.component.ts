import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SeasonService } from '../../services/season.service';
import { RoundDto } from '../../common/round-dto';
import { RoundsService } from '../../services/rounds.service';
import { MatchDto } from '../../common/match-dto';
import { MatchService } from "../../services/match.service";

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

    constructor(private route: ActivatedRoute,
                private router: Router,
                private seasonService: SeasonService,
                private roundsService: RoundsService,
                private matchService: MatchService) { }

    ngOnInit(): void {
        this.route.params.subscribe(params => {

            this.leagueId = +params['leagueId'];
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
        this.roundsService.getRounds(seasonId).subscribe(
            response => {
                if (Array.isArray(response.content)) {
                    this.rounds = response.content; // Assign the rounds to the rounds array
                    this.loadMatchesForRounds();
                } else {
                    console.error('Invalid data received for rounds:', response);
                }
            },
            error => {
                console.error('Error fetching rounds:', error);
            }
        );
    }


    loadMatchesForRounds(): void {
        this.rounds.forEach(round => {
            if (round.id !== undefined) {
                this.matchService.getMatches(round.id).subscribe(
                    response => {
                        round.matches = response.content; // Assuming matches are nested in 'content' property
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
        if (this.leagueId !== null) {
            this.seasonService.getSeason(this.leagueId, seasonId).subscribe(
                season => {
                    this.isCurrentSeason = season.currentSeason;
                    console.log(this.isCurrentSeason);
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
