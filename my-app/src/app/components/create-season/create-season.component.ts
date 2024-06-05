import { Component, OnInit } from '@angular/core';
import { SeasonDto } from '../../common/season-dto';
import { SeasonService } from '../../services/season.service';
import { Router, ActivatedRoute } from "@angular/router";
import {forkJoin} from "rxjs";
import {ClubDto} from "../../common/club-dto";
import {ClubService} from "../../services/club.service";
import {StandingService} from "../../services/standing.service";
import {StandingDTO} from "../../common/standing-dto";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {RoundsService} from "../../services/rounds.service";

@Component({
  selector: 'app-create-season',
  templateUrl: './create-season.component.html',
  styleUrls: ['./create-season.component.css']
})
export class CreateSeasonComponent implements OnInit {
  season: SeasonDto = new SeasonDto('', new Date(), new Date(), 0, 0, 0);
  leagueId!: number;
  clubs: ClubDto[] = [];
  standings:StandingDTO[] =[]
  errorMessage: string | null = null;
  numberOfStandings: number = 0;
  standingsForm: FormGroup[] = [];
  selectedClubs: ClubDto[] = [];


  constructor(
    private seasonService: SeasonService,
    private clubService: ClubService,
    private standingService: StandingService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private roundService:RoundsService
  ) { }

  fetchClubs() {
    this.clubService.getAllClubs().subscribe(
      clubs => {
        this.clubs = clubs;
      }
    )
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.leagueId = +params['id'];
    });
    this.fetchClubs();
  }

  onNumberOfStandingsChange(event: any) {
    this.numberOfStandings = event.target.value;
    this.generateStandingsForm();
  }

  generateStandingsForm() {
    this.standingsForm = [];
    for (let i = 0; i < this.numberOfStandings; i++) {
      this.standingsForm.push(this.formBuilder.group({
        standing: ['', Validators.required] // Add other fields as needed
      }));
    }
  }

  createSeason(): void {
    if (this.season.name.trim()) {
      this.season.leagueId = this.leagueId;
      const standings = [];
      for (let i = 0; i < this.standingsForm.length; i++) {
        standings.push(this.standingsForm[i].value);
      }
      this.season.start_date=new Date;
      this.seasonService.createSeason(this.leagueId,this.season).subscribe((id :number )=> {
        this.standingService.createStandings(id,this.selectedClubs).subscribe(data =>{
        })
      })
    } else {
      this.errorMessage = 'Season name cannot be empty.';
    }
  }
  getAvailableClubs(index: number): ClubDto[] {
    const selectedIds = this.selectedClubs
      .filter((club, i) => club && i !== index)
      .map(club => club!.id);
    return this.clubs.filter(club => !selectedIds.includes(club.id));
  }

  onClubChange(index: number, club: ClubDto) {
    this.selectedClubs[index] = club;
  }
  countArray(length: number): number[] {
    return Array.from({ length }, (_, i) => i);
  }

}
