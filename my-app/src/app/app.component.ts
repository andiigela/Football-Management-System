import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'my-app';
  userEmail: string | null = null;
  constructor(public authService: AuthService) {}
  ngOnInit(): void {
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
}
