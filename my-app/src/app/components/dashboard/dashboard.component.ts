import {Component, OnDestroy, OnInit} from '@angular/core';
import {WebSocketService} from "../../services/web-socket.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit,OnDestroy{
  constructor(private webSocket: WebSocketService) {
  }
  ngOnDestroy(): void {
    this.webSocket.disconnect();
  }
  ngOnInit(): void {
    this.webSocket.connect();
    this.webSocket.getMessages().subscribe(message =>{
      console.log("Message: " + message)
    })
  }


}
