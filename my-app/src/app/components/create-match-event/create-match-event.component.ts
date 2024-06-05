import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatchService} from "../../services/match.service";
import {MatchDto} from "../../common/match-dto";
import {ActivatedRoute, Router} from "@angular/router";
import {Matcheventrequest} from "../../common/matcheventrequest";
import {MatchEventType} from "../../enums/match-event-type";
import {NgForOf, NgIf} from "@angular/common";
import {PlayerDto} from "../../common/player-dto";
import {PlayerService} from "../../services/player.service";

@Component({
  selector: 'app-create-match-event',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './create-match-event.component.html',
  styleUrl: './create-match-event.component.css'
})
export class CreateMatchEventComponent implements OnInit{
  matchEventForm: FormGroup ;

  id!: number;
  match!: MatchDto;
  leagueId!: number;
  roundId!: number;
  seasonId!: number;
  matchEventType = Object.values(MatchEventType);
  selectedEventType : string = ' ';
  matchEventRequest:Matcheventrequest=new Matcheventrequest(   0, // id
    0, // minute
    '', // type
    0, // clubid
    0, // playerId
    0, // assistId
    0, // sub
    false, // isPenalty
    false, // isOwnGoal
    false, // isRedCard
    false, // isYellowCard
    '', // description
    0, // matchId);
  )
  playerDtoHome:PlayerDto[]=[];
  playerDtoAway:PlayerDto[]=[];
  arrayOfPlayers : PlayerDto[]=[];
  permArray : PlayerDto[]=[]






  constructor(private route:ActivatedRoute,private routes:Router,private matchService:MatchService,private playerService:PlayerService,private fb: FormBuilder) {

    this.matchEventForm = this.fb.group({
      type: [''],
      club_id: [''],
      playerId: [''],
      isOwnGoal: [false],
      isPenalty: [false],
      assistId: [''],
      isRedCard: [false],
      isYellowCard: [false],
      sub: [''],
      description: ['']
    });

    }
    ngOnInit(): void {
      this.route.params.subscribe(params => {
        this.id = +params['matchId'];
        this.leagueId = +params['leagueId'];
        this.seasonId = +params['seasonId'];
        this.roundId = +params['roundId'];
        this.loadMatchDetails(this.roundId, this.id);
      });
    }

  loadMatchDetails(roundId: number, matchId: number): void {
    this.matchService.getMatch(roundId, matchId).subscribe(
      match => {
        this.match = match;
        this.playerService.getPlayersByClubId(this.match.homeTeam.id).subscribe(data=>{
          this.playerDtoHome=data;
          for (let i = 0; i < this.playerDtoHome.length; i++) {
            this.playerDtoHome[i].clubId = this.match.homeTeam.id;
            this.arrayOfPlayers.push(this.playerDtoHome[i])
          }


        })
        this.playerService.getPlayersByClubId(this.match.awayTeam.id).subscribe(data=>{
          this.playerDtoAway=data;

          for (let i = 0; i < this.playerDtoAway.length; i++) {
            this.playerDtoAway[i].clubId = this.match.awayTeam.id;
            this.arrayOfPlayers.push(this.playerDtoAway[i])
          }

        })
      },
      error => {
        console.error('Error fetching match:', error);
      }
    );
  }


  onMatchEventTypeChange(event: any) {
    const selectedEventType = event.target.value;
    switch (selectedEventType) {
      case 'GOAL':
        this.selectedEventType = selectedEventType;
        // Reset other fields
        this.matchEventRequest.isRedCard = false;
        this.matchEventRequest.isYellowCard = false;
        // Hide assist field if one of isPenalty or isOwnGoal is checked
        if (this.matchEventRequest.isPenalty || this.matchEventRequest.isOwnGoal) {
          this.matchEventRequest.assist_id = 0;
        }
        break;
      case 'CARTON':
        this.selectedEventType = selectedEventType;
        // Reset other fields
        this.matchEventRequest.isOwnGoal = false;
        this.matchEventRequest.isPenalty = false;
        this.matchEventRequest.assist_id = 0;
        break;
      case 'REMOVED':
        this.selectedEventType = selectedEventType;
        // Handle logic for 'Removed' event type
        break;
      case 'POSTPONED':
        this.selectedEventType = selectedEventType;
        // Handle logic for 'Postponed' event type
        break;
      case 'SUB':
        this.selectedEventType = selectedEventType;
        // Reset other fields
        this.matchEventRequest.isOwnGoal = false;
        this.matchEventRequest.isPenalty = false;
        this.matchEventRequest.isRedCard = false;
        this.matchEventRequest.isYellowCard = false;
        break;
      default:
        // Reset all fields if no specific event type is selected
        this.resetFields();
        break;
    }
  }

  resetFields() {
    this.matchEventRequest.isOwnGoal = false;
    this.matchEventRequest.isPenalty = false;
    this.matchEventRequest.isRedCard = false;
    this.matchEventRequest.isYellowCard = false;
    this.matchEventRequest.assist_id = 0;
  }
  onGoalCheckboxChange(event: any) {
    if (this.matchEventRequest.isPenalty && this.matchEventRequest.isOwnGoal) {
      // Both checkboxes cannot be selected at the same time
      if (event.target.name === 'isPenalty') {
        // If Penalty is checked, uncheck Own Goal
        this.matchEventRequest.isOwnGoal = false;
      } else if (event.target.name === 'isOwnGoal') {
        // If Own Goal is checked, uncheck Penalty
        this.matchEventRequest.isPenalty = false;
      }
    } else {
      // If only one checkbox is checked, do nothing special
    }
  }

  cartonCheck(event:any){
    if (this.matchEventRequest.isYellowCard && this.matchEventRequest.isRedCard){
      if (event.target.name==="isYellowCard"){
        this.matchEventRequest.isRedCard=false;
      }else if (event.target.name === 'isRedCard'){
        this.matchEventRequest.isYellowCard=false
      }
    }else{

    }
  }







  createMatchEvent(  ){
    let matchDate = this.match.matchDate;

    // Ensure matchDate is a Date object
    if (!(matchDate instanceof Date)) {
      matchDate = new Date(matchDate);
    }

    // Now you can safely call getMinutes
    const matchMinutes = matchDate.getMinutes();
    const createMinutes = new Date().getMinutes();
    const minuteOfTheGame = createMinutes - matchMinutes;

    let matchEvent = new Matcheventrequest(0, minuteOfTheGame,this.matchEventForm.value.type,this.matchEventForm.value.club_id,this.matchEventForm.value.playerId,this.matchEventForm.value.assistId,this.matchEventForm.value.sub,this.matchEventForm.value.isPenalty,this.matchEventForm.value.isOwnGoal,this.matchEventForm.value.isRedCard,this.matchEventForm.value.isYellowCard,this.matchEventForm.value.description,this.id )
    console.log(matchEvent)
    this.matchService.createMatchEvent(this.roundId,this.id,matchEvent).subscribe(()=>{
      this.routes.navigateByUrl("/dashboard")
    },(err)=>{console.log(err)});
  }






  onTeamSelected() {
    const clubIdControl = this.matchEventForm.get('club_id');
    const empty:PlayerDto[]=[];

    if (clubIdControl) {
      const selectedTeamId = clubIdControl.value;
      console.log(selectedTeamId)
      const clubs = this.arrayOfPlayers.filter(p=>p.clubId==selectedTeamId);
      if (clubs.length!=0){
        console.log(clubs)
        this.permArray=clubs;
      }else {
        this.permArray=empty
      }

      // Add your team selection logic here
    } else {
      console.error('club_id control is not found in the form.');
    }
  }



}
