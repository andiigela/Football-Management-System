import { Component, OnInit } from '@angular/core';
import { SeasonDto } from "../../common/season-dto";
import { SeasonService } from "../../services/season.service";

@Component({
    selector: 'app-season',
    templateUrl: './season.component.html',
    styleUrls: ['./season.component.css']
})
export class SeasonComponent implements OnInit {
    seasons: SeasonDto[] = [];

    constructor(private seasonService: SeasonService) {}

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
}
