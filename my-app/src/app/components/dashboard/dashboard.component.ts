import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { AuthService } from '../../services/auth.service'; // Update the path

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  users: any[] = [];
  currentUserId: number | null = null; // Variable to store the current user's ID

  constructor(private http: HttpClient, private authService: AuthService) { } // Inject AuthService

  ngOnInit(): void {
    this.getCurrentUserId();
    this.loadUsers();
  }

  getCurrentUserId(): void {
    this.currentUserId = this.authService.getUserIdFromToken();
  }

  loadUsers(): void {
    if (this.currentUserId !== null) {

      this.http.get(`http://localhost:8080/api/users?currentUserId=${this.currentUserId}`).subscribe((res: any) => {
        this.users = JSON.parse(JSON.stringify(res));
      });
    } else {
      console.log("User ID not available in token.");
    }
  }

  updateUserStatus(userId: number, enabled: boolean): void {
    this.http.put(`http://localhost:8080/api/users/${userId}/status?enabled=${enabled}`, {}).subscribe(() => {
      const userToUpdate = this.users.find(user => user.id === userId);
      if (userToUpdate) {
        userToUpdate.enabled = enabled;
      }
    });
  }

  deleteUser(userId: number): void {
    this.http.delete(`http://localhost:8080/api/users/delete/${userId}`).subscribe(() => {
      this.users = this.users.filter(user => user.id !== userId);
    });
  }
}
