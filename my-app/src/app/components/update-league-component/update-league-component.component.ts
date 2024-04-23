import {Component, Input, OnInit} from '@angular/core';
import {LeagueDto} from "../../common/league-dto";
import {LeagueService} from "../../services/league.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-update-league-component',
  templateUrl: './update-league-component.component.html',
  styleUrls: ['./update-league-component.component.css']
})
export class UpdateLeagueComponent implements OnInit {
  league: LeagueDto = new LeagueDto(0, '', new Date(), new Date(), ''); // Initialize league with empty values

  constructor(private route: ActivatedRoute, private router: Router, private leagueService: LeagueService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const leagueId = params['id'];
      const leagueData = history.state.league; // Get league data passed from parent component

      if (leagueData) {
        this.league = leagueData;
      } else {
        // Fetch league data from backend using leagueId
      }
    });
  }

  updateLeague(): void {
    this.leagueService.editLeague(this.league.id, this.league).subscribe(() => {
      // Handle success
      this.router.navigate(['/league']); // Navigate back to the league route
    }, error => {
      // Handle error
      alert('Failed to update league. Please try again.'); // Show alert for error
    });
  }
}
