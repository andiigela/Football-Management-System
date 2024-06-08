import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { SeasonService } from '../../services/season.service';
import {LeagueService} from "../../services/league.service";
import {SeasonDto} from "../../common/season-dto";

@Component({
  selector: 'app-season',
  templateUrl: './season.component.html',
  styleUrls: ['./season.component.css']
})
export class SeasonComponent implements OnInit {

  seasons: SeasonDto[] = [];
  leagueId!: number;
  pageNumber: number = 1;
  pageSize: number = 2;
  totalElements: number = 0;

  constructor(private route: ActivatedRoute,
              private router: Router, private seasonService: SeasonService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.leagueId = +params['id'];
      this.fetchSeasonsForLeague();
    });
  }

    fetchSeasonsForLeague(): void {
        this.seasonService.getSeasons(this.leagueId, this.pageNumber-1, this.pageSize).subscribe(
            response => {
              this.seasons = response.content;
              this.totalElements = response.totalElements;
            },
            error => {
                console.error('Error fetching seasons:', error);
            }
        );
    }
    onPageChange(pageNumber: number): void {
        console.log('Page change to:', pageNumber);
        this.pageNumber = pageNumber;
        this.fetchSeasonsForLeague();

    }
    editSeason(id: number) {
        this.router.navigate(['/league', this.leagueId, 'seasons', 'edit-season', id]);
    }
    deleteSeason(seasonId: number): void {
        this.seasonService.deleteSeason(this.leagueId,seasonId).subscribe(
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
  addRound(seasonId: number): void {
    this.router.navigate(['/league', this.leagueId, 'seasons', seasonId, 'rounds']);
  }

  redirectToCreateSeason(): void {
        this.router.navigate(['/league', this.leagueId, 'create-season']);
    }

  generateRoundsAndMatches(seasonId: number) {
    this.seasonService.createRoundsAndMatches(this.leagueId,seasonId).subscribe(
      () => {
        console.log('Rounds and matches created  successfully');
        // Update seasons array to remove the deleted season
        this.seasons = this.seasons.filter(season => season.id !== seasonId);

      },
      error => {
        console.log('Error deleting season:', error);
      }
    );

  }
}
