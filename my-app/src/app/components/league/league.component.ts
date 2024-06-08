import { Component, OnInit } from '@angular/core';
import { LeagueDto } from "../../common/league-dto";
import { LeagueService } from "../../services/league.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-league',
  templateUrl: './league.component.html',
  styleUrls: ['./league.component.css']
})
export class LeagueComponent implements OnInit {

  leagues :LeagueDto[]= [];
  newLeague: LeagueDto = new LeagueDto(0, '', 0,'', '');
  pageNumber: number = 1;
  pageSize: number = 3;
  totalElements: number = 0;
  searchQuery: string = '';

  constructor(private leagueService: LeagueService, private router: Router) { }

  ngOnInit(): void {
    this.findAllLeagues();
  }
  findAllLeagues(){
    this.leagueService.returnAllLeagues(this.pageNumber-1, this.pageSize)
      .subscribe(
        response => {
          this.leagues = response.content;
          this.totalElements = response.totalElements;
          this.updatePlayerList(this.leagues); // Call updatePlayerList to set image paths
        },
        error => {
          console.error('Error fetching leagues:', error);
        }
      );

  }

  onPageChange(pageNumber: number): void {
    console.log('Page change to:', pageNumber);
    this.pageNumber = pageNumber;
    this.fetchLeagues(this.searchQuery);
  }

  searchLeagues(): void {
    this.fetchLeagues(this.searchQuery);
  }

  private fetchLeagues(query: string): void {
    if (query.trim() !== '') {
      this.leagueService.searchLeaguesByName(query, this.pageNumber - 1, this.pageSize)
          .subscribe(
              response => {
                this.leagues = response.content;
                this.totalElements = response.totalElements;
              },
              error => {
                console.error('Error fetching leagues:', error);
              }
          );
    } else {
      this.findAllLeagues();
    }
  }

  deleteLeague(id: number): void {
    this.leagueService.deleteLeague(id).subscribe(() => {
      this.leagues = this.leagues.filter(league => league.id !== id);
    });
  }
  updatePlayerList(leagueList: LeagueDto[]){
    leagueList.forEach(leagueDto => this.getLeagueImageUrl(leagueDto)) // set ImagePath to player.imagePath for each player
  }
  getLeagueImageUrl(leagueDto: LeagueDto):void {
    if (leagueDto.picture) {
      this.leagueService.getImageUrl(leagueDto.picture).subscribe((blob: Blob) => {
          const imageUrl = URL.createObjectURL(blob);
          leagueDto.picture = imageUrl;
        },
        error => {
          console.error(`Error fetching image for league: ${leagueDto.name}`, error);
          leagueDto.picture = ''; // Set default image path
        }
      );
    }
  }

  updateLeague(league: LeagueDto): void {
    this.router.navigate(['/update-league', league.id], {state: {league: league}});
  }
  redirectToCreateLeague(): void {
    this.router.navigate(['/create-league']);
  }

  redirectToLeagueSeasons(id: number): void {
    this.router.navigate(['/league', id, 'seasons']);
  }
}
