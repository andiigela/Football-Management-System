import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { SeasonService } from '../../services/season.service';
import {LeagueService} from "../../services/league.service";

@Component({
  selector: 'app-season',
  templateUrl: './season.component.html',
  styleUrls: ['./season.component.css']
})
export class SeasonComponent implements OnInit {

  seasons: any[] = [];
  leagueId!: number;

  constructor(private route: ActivatedRoute, private leagueService: LeagueService,
              private router: Router, private seasonService: SeasonService) { }

  ngOnInit(): void {
    // Retrieve the league id from the route parameters
    this.route.params.subscribe(params => {
      this.leagueId = +params['id']; // '+' is used to convert the string parameter to a number
      // Call a method to fetch seasons for the current league
      this.fetchSeasonsForLeague(this.leagueId);
    });
  }

    fetchSeasonsForLeague(leagueId: number): void {
        this.leagueService.getSeasonsForLeague(leagueId).subscribe(
            seasons => {

                this.seasons = seasons.filter(season => !season.deleted);
            },
            error => {
                console.error('Error fetching seasons:', error);
            }
        );
    }


    editSeason(id: number) {
        this.router.navigate(['/league', this.leagueId, 'seasons', 'edit-season', id]);
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
}
