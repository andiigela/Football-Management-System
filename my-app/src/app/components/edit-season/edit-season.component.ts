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
        this.fetchSeasonDetails();
    }
    updateSeason(): void {
        this.seasonService.updateSeason(this.seasonId, this.season).subscribe(
            () => {
                console.log('Season updated successfully');
                this.router.navigate(['/league']);
            },
            error => {
                console.log('Error updating season:', error);
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
