import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SeasonService } from '../../services/season.service';
import {LeagueService} from "../../services/league.service";

@Component({
  selector: 'app-season',
  templateUrl: './season.component.html',
  styleUrls: ['./season.component.css']
})
export class SeasonComponent implements OnInit {

  seasons: any[] = []; // Define a seasons array to hold the fetched seasons
  leagueId!: number; // Define a leagueId variable to hold the current league id

  constructor(private route: ActivatedRoute, private leagueService: LeagueService) { }

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
        this.seasons = seasons;
      },
      error => {
        console.error('Error fetching seasons:', error);
      }
    );
  }
}
