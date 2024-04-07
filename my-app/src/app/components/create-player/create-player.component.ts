import {Component, OnInit} from '@angular/core';
import {Foot} from "../../enums/foot";
import {FootballPosition} from "../../enums/football-position";

@Component({
  selector: 'app-create-player',
  templateUrl: './create-player.component.html',
  styleUrls: ['./create-player.component.css']
})
export class CreatePlayerComponent implements OnInit {
  footOptions = Object.values(Foot);
  positionOptions = Object.values(FootballPosition);
  ngOnInit(): void {
  }

}
