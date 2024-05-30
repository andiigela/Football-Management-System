import {Component, OnInit} from '@angular/core';
import {faBell} from "@fortawesome/free-solid-svg-icons";
import {WebSocketService} from "../../services/web-socket.service";
import {Router} from "@angular/router";
import {NotificationService} from "../../services/notification.service";
import {SharedNotificationService} from "../../services/shared-notification.service";

@Component({
  selector: 'app-notification-status',
  templateUrl: './notification-status.component.html',
  styleUrl: './notification-status.component.css'
})
export class NotificationStatusComponent implements OnInit {
  protected readonly faBell = faBell;
  protected notificationsCount: number = 0;
  constructor(private route: Router,private sharedNotification: SharedNotificationService) {
  }
  ngOnInit(): void {
    this.sharedNotification.fetchNotificationCountsFromApi();
    this.sharedNotification.readNotificationsFromWebSocketToIncrement();
    this.sharedNotification.retrieveNotificationsCount().subscribe(count => this.notificationsCount=count)

  }
  redirectToNotifications(){
    this.sharedNotification.resetNotificationCount();
    this.route.navigateByUrl("/notifications");
  }

}
