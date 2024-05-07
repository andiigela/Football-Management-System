import { Component, OnInit } from '@angular/core';
import { SeasonDto } from '../../common/season-dto';
import { SeasonService } from '../../services/season.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-season',
  templateUrl: './create-season.component.html',
  styleUrls: ['./create-season.component.css']
})
export class CreateSeasonComponent implements OnInit {
  season: SeasonDto = new SeasonDto('');

  constructor(private seasonService: SeasonService, private router: Router) { }

  ngOnInit(): void {
  }

  createSeason(): void {
    this.seasonService.createSeason(this.season).subscribe(
        () => {
          console.log('Season created successfully');
          this.router.navigate(['seasons']);

        },
        error => {
          console.log('Error creating season:', error);
        }
    );
  }
}
