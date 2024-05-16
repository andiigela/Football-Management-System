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
    leagueId!: number;
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
        // Retrieve seasonId and leagueId from URL parameters
        this.seasonId = this.route.snapshot.params['id'];
        this.leagueId = +this.route.snapshot.params['leagueId']; // Convert to number

        // Fetch season details
        this.fetchSeasonDetails();
    }

    updateSeason(): void {
        // Update season
        this.seasonService.editSeason(this.leagueId, this.seasonId, this.season).subscribe(
            () => {
                console.log('Season updated successfully');
                this.router.navigate(['/league']);
            },
            (error: any) => {
                console.log('Error updating season:', error);
            }
        );
    }

    private fetchSeasonDetails(): void {
        // Fetch season details
        this.seasonService.getSeason(this.leagueId, this.seasonId).subscribe(
            (data: SeasonDto) => {
                this.season = data;
            },
            (error: any) => {
                console.log('Error fetching season:', error);
            }
        );
    }
}
