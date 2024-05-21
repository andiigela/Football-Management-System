import {Component, OnInit} from '@angular/core';
import {faBell} from "@fortawesome/free-solid-svg-icons";
import {WebSocketService} from "../../services/web-socket.service";

@Component({
  selector: 'app-notification-status',
  templateUrl: './notification-status.component.html',
  styleUrl: './notification-status.component.css'
})
export class NotificationStatusComponent implements OnInit {
  protected readonly faBell = faBell;
  protected countNotification: number = 0;
  constructor(private webSocketService: WebSocketService) {
  }
  ngOnInit(): void {
    this.webSocketService.getMessages().subscribe(notification => {
      if(notification){
        console.log("Message: " + notification);
        this.webSocketService.incrementNotificationCount();
      }


    })
    this.webSocketService.getNotificationCount().subscribe(currentCount => {
      this.countNotification=currentCount;
    })
  }



}
