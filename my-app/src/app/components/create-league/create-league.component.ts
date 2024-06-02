import { Component } from '@angular/core';
import {LeagueDto} from "../../common/league-dto";
import {LeagueService} from "../../services/league.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-league',
  templateUrl: './create-league.component.html',
  styleUrls: ['./create-league.component.css']
})
export class CreateLeagueComponent {
  selectedFile: File|null = null;
  newLeague: LeagueDto = new LeagueDto(0, '', 0,'', ''); // Assuming LeagueDto is defined

  constructor(private leagueService: LeagueService, private router: Router) {}

  createLeague(): void {
    console.log(this.selectedFile,this.newLeague)
    this.leagueService.createLeague(this.newLeague,this.selectedFile!).subscribe(() => {
      // Handle success
      alert('League created successfully!');
      // Clear the form fields after successful creation
      this.newLeague = new LeagueDto(0, '', 0,'', '');
      this.router.navigate(['/league']);
    }, error => {
      // Handle error
      alert('Failed to create league. Please try again.');

    });
  }


  onFileChange(event: any) {
    this.selectedFile=event.target.files[0];
  }


}
