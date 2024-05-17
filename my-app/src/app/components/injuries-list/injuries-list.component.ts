import {Component, OnInit} from '@angular/core';
import {PlayerDto} from "../../common/player-dto";
import {InjuryDto} from "../../common/injury-dto";
import {PlayerService} from "../../services/player.service";
import {ActivatedRoute, Router} from "@angular/router";
import {InjuryService} from "../../services/injury.service";

@Component({
  selector: 'app-injuries-list',
  templateUrl: './injuries-list.component.html',
  styleUrl: './injuries-list.component.css'
})
export class InjuriesListComponent implements OnInit {
  playerInjuriesList: InjuryDto[]=[];
  pageNumber: number = 1;
  pageSize: number = 5;
  totalElements: number = 0;
  currentPlayerId: number = 0;
  constructor(private injuryService: InjuryService,private router: Router,private activatedRoute: ActivatedRoute) {
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params=>{
      this.currentPlayerId = +params['id']
      this.getInjuries();
    })
  }
  public getInjuries(){
    this.injuryService.retrieveInjuries(this.currentPlayerId,this.pageNumber-1,this.pageSize).subscribe((response)=>{
      this.playerInjuriesList = response.content;
      this.totalElements = response.totalElements;
      console.log(this.totalElements)
    })
  }
  OnPageChange(pageNumber: number){
    this.pageNumber = pageNumber;
    this.getInjuries();
  }
  redirectToCreateInjury(){
    this.router.navigate([`/players/${this.currentPlayerId}/injuries/create`])
  }
  editInjury(injuryId: number){
    this.router.navigate([`/players/${this.currentPlayerId}/injuries/${injuryId}/edit`])
  }
  deleteInjury(injuryId: number){
    this.injuryService.deleteInjury(this.currentPlayerId,injuryId).subscribe(()=>{
      this.playerInjuriesList = this.playerInjuriesList.filter(injury => injury.id != injuryId)
    });
  }
}
