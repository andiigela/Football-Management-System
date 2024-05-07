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
        this.seasonService.getSeasonById(this.seasonId).subscribe(
            (data: SeasonDto) => {
                this.season = data;
            },
            error => {
                console.log('Error fetching season:', error);
            }
        );

        // Fetch all matches
        this.matchService.listAllMatches().subscribe(
            (data: MatchDto[]) => {
                this.matches = data;
            },
            error => {
                console.log('Error fetching matches:', error);
            }
        );
    }

    updateSeason(): void {
        this.seasonService.updateSeason(this.seasonId, this.season).subscribe(
            () => {
                console.log('Season updated successfully');
                // Redirect to the season details page or any other route after update
                this.router.navigate(['/season', this.seasonId]);
            },
            error => {
                console.log('Error updating season:', error);
            }
        );
    }

    removeSelectedMatches(): void {
        // Filter out the selected matches from the selectedMatchIds array
        this.selectedMatchIds = this.selectedMatchIds.filter(matchId => !this.matches.some(match => match.id === matchId));
    }
}
