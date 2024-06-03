import { Injectable } from '@angular/core';
import {NotificationService} from "./notification.service";
import {BehaviorSubject, Observable} from "rxjs";
import {WebSocketService} from "./web-socket.service";

@Injectable({
  providedIn: 'root'
})
export class SharedNotificationService {
  private notificationCountSubject: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  private connectionId: string = "notification";
  constructor(private notificationService: NotificationService,private webSocketService: WebSocketService) { }
  readNotificationsFromWebSocketToIncrement(){
    this.webSocketService.getMessages(this.connectionId).subscribe(notification => {
      if(notification){
        this.incrementNotificationCount();
      }
    })
  }
  fetchNotificationCountsFromApi(){
    this.notificationService.retrieveNotificationsCount().subscribe(count => {
      this.notificationCountSubject.next(count);
    })
  }
  incrementNotificationCount(): void{
    const currentCount = this.notificationCountSubject.value;
    this.notificationCountSubject.next(currentCount + 1);
  }
  resetNotificationCount() {
    const number_zero = 0;
    this.notificationService.updateNotificationsCount(number_zero).subscribe(()=>{
      this.notificationCountSubject.next(number_zero);
    })
  }
  public retrieveNotificationsCount(): Observable<number>{
    return this.notificationCountSubject.asObservable();
  }

}
