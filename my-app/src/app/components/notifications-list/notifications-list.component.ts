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
  deletedPlayerIds: PlayerIdDto[] = [];
  constructor(private notificationService: NotificationService,private playerService: PlayerService) {
  }
  ngOnInit(): void {
    this.playerService.getDeletedPlayerIds().subscribe((ids: PlayerIdDto[]) => {
      this.deletedPlayerIds = ids;

      // Retrieve notifications and update their permissions
      this.notificationService.retrieveNotifications().subscribe(notifications => {
        this.notificationsList = notifications;
        this.notificationsList.forEach(notification => this.updateNotificationPermission(notification));
      });
    });
  }
  // protected giveDeletePlayerPermission(notificationDto: NotificationDto){
  //   this.playerService.acceptDeletePlayerPermission(notificationDto.playerId).subscribe(data=>{
  //     console.log("Accepted Deleted player:  " + data);
  //     this.updateNotificationPermission(notificationDto);
  //   });
  // }
  private updateNotificationPermission(notificationDto: NotificationDto): void {
    // Check if the notification's playerId is in the list of deleted player IDs
    if (this.deletedPlayerIds.some(playerIdDto => playerIdDto.id === notificationDto.playerId)) {
      notificationDto.permissionGiven = true;
    }
  }
    giveDeletePlayerPermission(notificationDto: NotificationDto): void {
      this.playerService.acceptDeletePlayerPermission(notificationDto.playerId).subscribe(data => {
        console.log("Accepted Deleted player: " + data);
        notificationDto.permissionGiven = true;
    });
  }
}
