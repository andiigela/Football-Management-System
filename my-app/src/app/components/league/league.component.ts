import {Component, OnInit} from '@angular/core';
import {LeagueDto} from "../../common/league-dto";
import {LeagueService} from "../../services/league.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-league',
  templateUrl: './league.component.html',
  styleUrls: ['./league.component.css']
})
export class LeagueComponent implements OnInit{

  leagues :LeagueDto[]= [];
  newLeague: LeagueDto = new LeagueDto(0, '', new Date(), new Date(), '');
  pageNumber: number = 1;
  pageSize: number = 1;
  totalElements: number = 0;

  constructor(private leagueService : LeagueService,private router: Router) {
  }
    ngOnInit(): void {
      this.findAllLeagues()
    }
    findAllLeagues(){
      this.leagueService.returnAllLeagues(this.pageNumber-1, this.pageSize)
        .subscribe(
            response => {
                this.leagues = response.content;
                this.totalElements = response.totalElements;
            },
            error => {
              console.error('Error fetching leagues:', error);
            }
        );
    }
  onPageChange(pageNumber: number): void {
    console.log('Page change to:', pageNumber);
    this.pageNumber = pageNumber;
    this.findAllLeagues();

  }

  deleteLeague(id: number): void {
    this.leagueService.deleteLeague(id).subscribe(() => {
      // If deletion is successful, remove the league from the local array
      this.leagues = this.leagues.filter(league => league.id !== id);
    });
  }

  updateLeague(league: LeagueDto): void {
    this.router.navigate(['/update-league', league.id], { state: { league: league } });
  }
  redirectToCreateLeague(): void {
    this.router.navigate(['/create-league']);
  }

  redirectToLeagueSeasons(id: number): void {
    this.router.navigate(['/league', id, 'seasons']);
  }
}
