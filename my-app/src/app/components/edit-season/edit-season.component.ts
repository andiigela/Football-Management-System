import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SeasonService } from '../../services/season.service';
import { MatchService } from '../../services/match.service';
import { SeasonDto } from '../../common/season-dto';
import { MatchDto } from '../../common/match-dto';

@Component({
    selector: 'app-edit-season',
    templateUrl: './edit-season.component.html',
    styleUrls: ['./edit-season.component.css']
})
export class EditSeasonComponent implements OnInit {
    seasonId!: number;
    season!: SeasonDto;
    matches!: MatchDto[];
    selectedMatchIds: number[] = [];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private seasonService: SeasonService,
        private matchService: MatchService
    ) {}

    ngOnInit(): void {
        this.seasonId = this.route.snapshot.params['id'];
        // Fetch season details
        this.fetchSeasonDetails();

        // Fetch all matches
        this.matchService.listAllMatches().subscribe(
            (data: MatchDto[]) => {
                // Filter out matches already in the season
                this.matches = data.filter(match => !this.isMatchInSeason(match));
            },
            error => {
                console.log('Error fetching matches:', error);
            }
        );
    }
    isMatchInSeason(match: MatchDto): boolean {
        return this.season.matches.some(seasonMatch => seasonMatch.id === match.id);
    }
    updateSeason(): void {
        // Filter out any undefined values from the selectedMatchIds array
        const selectedMatches = this.selectedMatchIds
            .map(id => this.matches.find(match => match.id === id))
            .filter(match => match !== undefined) // Filter out undefined matches
            .map(match => match as MatchDto); // Cast the matches to MatchDto

        // Assign the filtered array to season.matches
        this.season.matches = selectedMatches;

        // Update the season on the server
        this.seasonService.updateSeason(this.seasonId, this.season).subscribe(
            () => {
                console.log('Season updated successfully');
                this.router.navigate(['/seasons']);
            },
            error => {
                console.log('Error updating season:', error);
            }
        );
    }

    removeMatch(match: MatchDto): void {
        const matchId = match.id;
        const seasonId = this.seasonId;
        this.seasonService.removeMatchFromSeason(seasonId, matchId).subscribe(
            () => {
                console.log('Match removed successfully');
                // Remove the match from the season's matches array
                this.season.matches = this.season.matches.filter(m => m.id !== match.id);
                // Add the removed match back to the available matches
                this.matches.push(match);
            },
            error => {
                console.log('Error removing match:', error);
            }
        );
    }



    addMatchToSeason(match: MatchDto): void {
        const seasonId = this.seasonId;
        const matchIds: number[] = [match.id]; // Assuming match has an 'id' property
        this.seasonService.addMatchesToSeason(seasonId, matchIds).subscribe(
            () => {
                console.log('Match added successfully');
                // Remove the added match from the available matches
                this.matches = this.matches.filter(m => m.id !== match.id);
                // After adding the match, update the season details
                this.fetchSeasonDetails();
            },
            error => {
                console.log('Error adding match:', error);
            }
        );
    }


    private fetchSeasonDetails(): void {
        // Fetch season details again after making changes
        this.seasonService.getSeasonById(this.seasonId).subscribe(
            (data: SeasonDto) => {
                this.season = data;
            },
            error => {
                console.log('Error fetching season:', error);
            }
        );
    }

}
