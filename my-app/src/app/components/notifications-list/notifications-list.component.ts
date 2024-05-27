import {Component, OnInit} from '@angular/core';
import {NotificationService} from "../../services/notification.service";
import {NotificationDto} from "../../common/notification-dto";
import {PlayerService} from "../../services/player.service";
import {PlayerIdDto} from "../../common/player-id-dto";

@Component({
  selector: 'app-notifications-list',
  templateUrl: './notifications-list.component.html',
  styleUrl: './notifications-list.component.css'
})
export class NotificationsListComponent implements OnInit{
  notificationsList: NotificationDto[] = [];
  askedPermissionPlayerIds: PlayerIdDto[] = [];
  constructor(private notificationService: NotificationService,private playerService: PlayerService) {
  }
  ngOnInit(): void {
    this.playerService.getDeletedPlayerIds().subscribe((ids: PlayerIdDto[]) => {
      this.askedPermissionPlayerIds = ids;
      this.notificationService.retrieveNotifications().subscribe(notifications => {
        this.notificationsList = notifications;
        this.notificationsList.forEach(notification => this.updateNotificationPermission(notification));
      });
    });
  }
  private updateNotificationPermission(notificationDto: NotificationDto): void {
    if (this.askedPermissionPlayerIds.some(playerIdDto => playerIdDto.id === notificationDto.playerId)) {
      notificationDto.permissionGiven = true;
    }
  }
    giveDeletePlayerPermission(notificationDto: NotificationDto): void {
      this.playerService.acceptDeletePlayerPermission(notificationDto.playerId).subscribe(data => {
        notificationDto.permissionGiven = true;
    });
  }
}
