import { Component } from '@angular/core';
import {LeagueDto} from "../../common/league-dto";
import {LeagueService} from "../../services/league.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-league',
  templateUrl: './create-league.component.html',
  styleUrls: ['./create-league.component.css']
})
export class CreateLeagueComponent {
  newLeague: LeagueDto = new LeagueDto(0, '', new Date(), new Date(), ''); // Assuming LeagueDto is defined

  constructor(private leagueService: LeagueService, private router: Router) {}

  createLeague(): void {
    this.leagueService.createLeague(this.newLeague).subscribe(() => {
      // Handle success
      alert('League created successfully!');
      // Clear the form fields after successful creation
      this.newLeague = new LeagueDto(0, '', new Date(), new Date(), '');
      // Reload the page to refresh the list of leagues
      window.location.reload();
    }, error => {
      // Handle error
      alert('Failed to create league. Please try again.'); // Show alert for error
    });
  }


}
