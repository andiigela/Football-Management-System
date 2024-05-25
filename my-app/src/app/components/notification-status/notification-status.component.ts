import {Component, OnInit} from '@angular/core';
import {faBell} from "@fortawesome/free-solid-svg-icons";
import {WebSocketService} from "../../services/web-socket.service";
import {Router} from "@angular/router";
import {NotificationService} from "../../services/notification.service";

@Component({
  selector: 'app-notification-status',
  templateUrl: './notification-status.component.html',
  styleUrl: './notification-status.component.css'
})
export class NotificationStatusComponent implements OnInit {
  protected readonly faBell = faBell;
  protected countNotification: number = 0;
  private connectionId: string = "notification";
  constructor(private webSocketService: WebSocketService,private notificationService: NotificationService,private route: Router) {
  }
  ngOnInit(): void {
    this.webSocketService.getMessages(this.connectionId).subscribe(notification => {
      if(notification){
        console.log("Message: " + notification);
        this.webSocketService.incrementNotificationCount();
      }
    })
    this.webSocketService.getNotificationCount().subscribe(currentCount => {
      this.countNotification=currentCount;
    });
    this.notificationService.retrieveNotificationsCount().subscribe(data => {
      this.showNotificationsNumber(data);
      console.log("My Counts: " + data);
    })
  }
  redirectToNotifications(){
    this.countNotification=0;
    this.route.navigateByUrl("/notifications");
  }
  showNotificationsNumber(countFromAPI: string){
    if(this.countNotification == 0 && parseInt(countFromAPI) != 0){
      this.countNotification=parseInt(countFromAPI);
    }

  }


}
