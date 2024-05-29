import {Component, OnDestroy, OnInit} from '@angular/core';
import {NotificationService} from "../../services/notification.service";
import {NotificationDto} from "../../common/notification-dto";
import {PlayerService} from "../../services/player.service";
import {PlayerIdDto} from "../../common/player-id-dto";
import {WebSocketService} from "../../services/web-socket.service";
import {SharedNotificationService} from "../../services/shared-notification.service";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-notifications-list',
  templateUrl: './notifications-list.component.html',
  styleUrl: './notifications-list.component.css'
})
export class NotificationsListComponent implements OnInit{
  notificationsList: NotificationDto[] = [];
  askedPermissionPlayerIds: PlayerIdDto[] = [];
  notificationDto: NotificationDto|null=null;

  private connectionId: string = "notification";
  constructor(private notificationService: NotificationService,
              private playerService: PlayerService,
              private webSocketService: WebSocketService,
              private sharedNotificationService: SharedNotificationService,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.sharedNotificationService.resetNotificationCount()
    this.playerService.getDeletedPlayerIds().subscribe((ids: PlayerIdDto[]) => {
      this.askedPermissionPlayerIds = ids;
      this.notificationService.retrieveNotifications().subscribe(notifications => {
        this.notificationsList = notifications;
        this.notificationsList.forEach(notification => this.updateNotificationPermission(notification));
      });
    });
    this.webSocketService.getMessages(this.connectionId).subscribe((data: any[]) => {
      if(data.length > 0){
        if(typeof data === "string"){
           this.sharedNotificationService.resetNotificationCount()
           this.notificationDto = JSON.parse(data);
           this.notificationsList.push(this.notificationDto!);
        }
      }
    })
  }
  private updateNotificationPermission(notificationDto: NotificationDto): void {
    if (this.askedPermissionPlayerIds.some(playerIdDto => playerIdDto.id === notificationDto.playerId)) {
      notificationDto.permissionGiven = true;
    }
  }
  protected giveDeletePlayerPermission(notificationDto: NotificationDto): void {
      this.playerService.acceptDeletePlayerPermission(notificationDto.playerId).subscribe(data => {
        notificationDto.permissionGiven = true;
    });
  }
  protected deleteNotification(notificationDto: NotificationDto){
    this.notificationService.deleteNotification(this.authService.getUserIdFromToken()!,notificationDto).subscribe(()=>{
      this.notificationsList = this.notificationsList.filter(notification => notification.id != notificationDto.id);
    });
  }

}
