import {Component, OnInit} from '@angular/core';
import {NotificationService} from "../../services/notification.service";
import {NotificationDto} from "../../common/notification-dto";

@Component({
  selector: 'app-notifications-list',
  templateUrl: './notifications-list.component.html',
  styleUrl: './notifications-list.component.css'
})
export class NotificationsListComponent implements OnInit{
  notificationsList: NotificationDto[] = [];
  constructor(private notificationService: NotificationService) {
  }
  ngOnInit(): void {
    this.notificationService.retrieveNotifications().subscribe(notifications => this.notificationsList=notifications)
  }

}
