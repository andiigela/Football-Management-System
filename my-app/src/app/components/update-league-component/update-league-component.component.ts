import { Component, OnInit } from '@angular/core';
import { LeagueDto } from "../../common/league-dto";
import { LeagueService } from "../../services/league.service";
import { ActivatedRoute, Router } from "@angular/router";

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
      const leagueData = history.state.league;

      if (leagueData) {
        // Convert string dates to Date objects
        leagueData.startDate = new Date(leagueData.startDate);
        leagueData.endDate = new Date(leagueData.endDate);
        this.league = leagueData;
      }
    });
  }

  updateLeague(): void {
    this.leagueService.editLeague(this.league.dbId, this.league).subscribe(() => {
      this.router.navigate(['/league']);
    }, error => {
      alert('Failed to update league. Please try again.');
    });
  }
}
