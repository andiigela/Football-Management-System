import {Component, OnInit} from '@angular/core';
import {faBell} from "@fortawesome/free-solid-svg-icons";
import {WebSocketService} from "../../services/web-socket.service";
import {Router} from "@angular/router";
import {NotificationService} from "../../services/notification.service";
import {BehaviorSubject, Observable} from "rxjs";

@Component({
  selector: 'app-notification-status',
  templateUrl: './notification-status.component.html',
  styleUrl: './notification-status.component.css'
})
export class NotificationStatusComponent implements OnInit {
  protected readonly faBell = faBell;
  private notificationCount :BehaviorSubject<number> = new BehaviorSubject<number>(0);
  protected notificationsCount: number = 0;
  private connectionId: string = "notification";
  constructor(private webSocketService: WebSocketService,private notificationService: NotificationService,private route: Router) {
  }
  ngOnInit(): void {
    this.retrieveNotificationsNumberFromAPI();
    this.webSocketService.getMessages(this.connectionId).subscribe(notification => {
      if(notification){
        console.log("Message: " + notification);
        this.incrementNotificationCount();
      }
    })
    this.notificationCount.subscribe(currentCount => {
      this.notificationsCount=currentCount;
    });
  }
  retrieveNotificationsNumberFromAPI(){
    this.notificationService.retrieveNotificationsCount().subscribe(count => {
      this.notificationCount.next(count);
    })
  }
  redirectToNotifications(){
    this.notificationsCount=0;
    this.notificationService.updateNotificationsCount(this.notificationsCount);
    this.route.navigateByUrl("/notifications");
  }
  incrementNotificationCount(): void{
    this.notificationCount.next(this.notificationsCount + 1);
  }
}
