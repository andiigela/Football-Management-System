import { Component, OnInit } from '@angular/core';
import { SeasonService } from '../../services/season.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RoundDto } from '../../common/round-dto';

@Component({
  selector: 'app-create-round',
  templateUrl: './create-round.component.html',
  styleUrls: ['./create-round.component.css']
})
export class CreateRoundComponent implements OnInit {
  seasonId!: number;
  newRound: RoundDto = {

    start_date: new Date(),
    end_date: new Date()
  };

  constructor(
      private seasonService: SeasonService,
      private route: ActivatedRoute,
      private router: Router
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.seasonId = +params['seasonId'];
    });
  }

  createRound(): void {
    this.seasonService.createRoundsForSeason(this.seasonId, this.newRound).subscribe(
        () => {
          console.log('Round created successfully');
          // Navigate back to the rounds page
          this.router.navigate(['/league', 2, 'seasons', this.seasonId, 'rounds']);
        },
        error => {
          console.error('Error creating round:', error);
          console.log(this.newRound);
        }
    );
  }
}
