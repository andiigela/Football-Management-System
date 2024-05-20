import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SeasonService } from '../../services/season.service';
import { SeasonDto } from '../../common/season-dto';

@Component({
  selector: 'app-edit-season',
  templateUrl: './edit-season.component.html',
  styleUrls: ['./edit-season.component.css']
})
export class EditSeasonComponent implements OnInit {
  seasonId!: number;
  leagueId!: number;
  season!: SeasonDto;
  errorMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private seasonService: SeasonService
  ) {}

  ngOnInit(): void {
    this.seasonId = this.route.snapshot.params['id'];
    this.leagueId = +this.route.snapshot.params['leagueId'];
    this.fetchSeasonDetails();
  }

  updateSeason(): void {
    if (this.season.name.trim()) {
      this.seasonService.editSeason(this.leagueId, this.seasonId, this.season).subscribe(
        () => {
          console.log('Season updated successfully');
          this.router.navigate(['/league']);
        },
        (error: any) => {
          console.log('Error updating season:', error);
        }
      );
    } else {
      this.errorMessage = 'Season name cannot be empty.';
    }
  }

  private fetchSeasonDetails(): void {
    this.seasonService.getSeason(this.leagueId, this.seasonId).subscribe(
      (data: SeasonDto) => {
        this.season = data;
      },
      (error: any) => {
        console.log('Error fetching season:', error);
      }
    );
  }
}
