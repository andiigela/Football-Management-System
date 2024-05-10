import { Component, OnInit } from '@angular/core';
import { SeasonDto } from '../../common/season-dto';
import { SeasonService } from '../../services/season.service';
import { Router, ActivatedRoute } from "@angular/router";

@Component({
    selector: 'app-create-season',
    templateUrl: './create-season.component.html',
    styleUrls: ['./create-season.component.css']
})
export class CreateSeasonComponent implements OnInit {
    season: SeasonDto = new SeasonDto('', 0);
    leagueId!: number;

    constructor(
        private seasonService: SeasonService,
        private router: Router,
        private route: ActivatedRoute // Inject ActivatedRoute
    ) { }

    ngOnInit(): void {
        // Retrieve leagueId from route parameters
        this.route.params.subscribe(params => {
            this.leagueId = +params['id']; // Assuming 'leagueId' is the parameter name
        });
    }

    createSeason(): void {
        this.seasonService.createSeason(this.leagueId, this.season).subscribe(
            () => {
                console.log('Season created successfully');
                this.router.navigate(['/league']);
            },
            error => {
                console.log('Error creating season:', error);
            }
        );
    }
}
