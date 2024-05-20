import { Component, OnInit } from '@angular/core';
import { SeasonService } from '../../services/season.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RoundDto } from '../../common/round-dto';
import { RoundsService } from "../../services/rounds.service";

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
  error: string | null = null;

  constructor(
    private seasonService: SeasonService,
    private route: ActivatedRoute,
    private router: Router,
    private roundService: RoundsService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.seasonId = +params['seasonId'];
    });
  }

  createRound(): void {
    if (this.isValidDateRange() && this.newRound.start_date && this.newRound.end_date) {
      this.roundService.createRound(this.seasonId, this.newRound).subscribe(
        () => {
          console.log('Round created successfully');
          // Navigate back to the rounds page
          this.router.navigate(['/league', 2, 'seasons', this.seasonId, 'rounds']);
        },
        error => {
          console.error('Error creating round:', error);
          this.error = 'Error creating round. Please try again.';
        }
      );
    } else {
      this.error = 'Please select both start and end dates.';
    }
  }

  isValidDateRange(): boolean {
    return new Date(this.newRound.start_date) < new Date(this.newRound.end_date);
  }
}
