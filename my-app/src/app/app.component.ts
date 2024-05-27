import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';
import { Observable, of } from 'rxjs';
import {faBell} from "@fortawesome/free-solid-svg-icons";
import {WebSocketService} from "./services/web-socket.service";
import {SharedNotificationService} from "./services/shared-notification.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'my-app';
  userEmail: string | null = null;
  private connectionId: string = "notification";
  constructor(public authService: AuthService,private webSocketService: WebSocketService) {}
  ngOnInit(): void {
    this.authService.isAuthenticated.subscribe(val => {
      if(val == true){
        this.webSocketService.connect("/topic/notifications/askedpermission",this.connectionId);
      }
    })
    this.authService.getUserEmail().subscribe(email => {
      this.userEmail = email;
    });
  }
  isAdmin(): Observable<boolean> {
    const userRole = this.authService.getRoleFromToken();
    return of(userRole === 'ADMIN_LEAGUE');
  }
  isClub(): Observable<boolean> {
    const userRole = this.authService.getRoleFromToken();
    return of(userRole === 'ADMIN_CLUB');
  }
  logout(): void {
    this.authService.logout();
  }
  get userId(): number | null {
    return this.authService.getUserIdFromToken();
  }
}
