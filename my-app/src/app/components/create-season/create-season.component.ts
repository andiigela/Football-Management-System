import { Component, OnInit } from '@angular/core';
import { SeasonDto } from '../../common/season-dto';
import { SeasonService } from '../../services/season.service';
import { Router, ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-create-season',
  templateUrl: './create-season.component.html',
  styleUrls: ['./create-season.component.css']
})
export class CreateSeasonComponent implements OnInit {
  season: SeasonDto = new SeasonDto('', 0);
  leagueId!: number;
  errorMessage: string | null = null;

  constructor(
    private seasonService: SeasonService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.leagueId = +params['id'];
    });
  }

  createSeason(): void {
    if (this.season.name.trim()) {
      this.seasonService.createSeason(this.leagueId, this.season).subscribe(
        () => {
          console.log('Season created successfully');
          this.router.navigate(['/league']);
        },
        error => {
          console.log('Error creating season:', error);
        }
      );
    } else {
      this.errorMessage = 'Season name cannot be empty.';
    }
  }
}
