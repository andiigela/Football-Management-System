import {Component, OnInit} from '@angular/core';
import {PlayerService} from "../../services/player.service";
import {PlayerDto} from "../../common/player-dto";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Foot} from "../../enums/foot";
import {FootballPosition} from "../../enums/football-position";

@Component({
  selector: 'app-player-edit',
  templateUrl: './player-edit.component.html',
  styleUrls: ['./player-edit.component.css']
})
export class PlayerEditComponent implements OnInit{
  playerEdit: PlayerDto = new PlayerDto("",0,0,0,"","");
  editForm: FormGroup;
  footOptions = Object.values(Foot);
  positionOptions = Object.values(FootballPosition);
  file: File|null = null;
  constructor(private playerService: PlayerService,private route: ActivatedRoute,private formBuilder: FormBuilder,
              private router: Router) {
    this.editForm = this.formBuilder.group({
      name: [''],
      height: [''],
      weight: [''],
      shirtNumber: [''],
      preferred_foot: ['LEFT'],
      position: ['GOALKEEPER'],
    })
  }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const playerId = params["id"]
      this.getPlayer(playerId);
    })
  }
  updatePlayer(){
    const formValue = this.editForm.value
    let playerEditInfo = new PlayerDto(formValue.name, formValue.height, formValue.weight,
        formValue.shirtNumber, formValue.preferred_foot, "");
    playerEditInfo.id = this.playerEdit.id;
    this.playerService.updatePlayer(playerEditInfo,this.file!)
        .subscribe(()=>{
          this.router.navigate(["/players"])
        });
  }
  getPlayer(playerId: number){
    this.playerService.retrievePlayer(playerId).subscribe((player)=>{
      this.playerEdit = new PlayerDto(player.name,player.height,player.weight,player.shirtNumber,player.preferred_foot,"");
      this.playerEdit.id=player.id
      this.getEditPlayerImageUrl(player.imagePath);
      this.editForm.patchValue({
        name: player.name,
        height: player.height,
        weight: player.weight,
        shirtNumber: player.shirtNumber,
        preferred_foot: player.preferred_foot,
        position: 'GOALKEEPER'
      });
    })
  }
  getEditPlayerImageUrl(imagePath: string){
    this.playerService.getImageUrl(imagePath).subscribe((blob: Blob) => {
      const imageUrl = URL.createObjectURL(blob);
      this.playerEdit.imagePath = imageUrl;
    })
  }
  onFileSelected(event: any){
    const file: File= event.target.files[0];
    this.file = file;
    console.log(this.file)
  }

}
