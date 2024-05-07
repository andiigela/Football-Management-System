import { Component, OnInit } from '@angular/core';
import { SeasonDto } from "../../common/season-dto";
import { SeasonService } from "../../services/season.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-season',
    templateUrl: './season.component.html',
    styleUrls: ['./season.component.css']
})
export class SeasonComponent implements OnInit {
    seasons: SeasonDto[] = [];

    constructor(private seasonService: SeasonService, private router: Router) {}

    ngOnInit(): void {
        this.loadSeasons();
    }

    loadSeasons() {
        this.seasonService.getAllSeasons().subscribe(
            (data: SeasonDto[]) => {
                this.seasons = data.filter(season => !season.deleted);
                console.log(this.seasons);
            },
            error => {
                console.log('Error fetching seasons:', error);
            }
        );
    }
    editSeason(id: number) {
        this.router.navigate(['seasons/edit-season', id]);
    }
    deleteSeason(seasonId: number): void {
        this.seasonService.deleteSeason(seasonId).subscribe(
            () => {
                console.log('Season deleted successfully');
                // Update seasons array to remove the deleted season
                this.seasons = this.seasons.filter(season => season.id !== seasonId);

            },
            error => {
                console.log('Error deleting season:', error);
            }
        );
    }
    navigateToCreateSeason() {
        this.router.navigate(['/create-season']);
    }
}
