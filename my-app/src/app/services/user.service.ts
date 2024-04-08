import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

    getUsers(currentUserId: number | null): Observable<any[]> {
        if (currentUserId !== null) {
            return this.http.get<any[]>(`${this.apiUrl}?currentUserId=${currentUserId}`);
        } else {
            console.log("User ID not available.");
            return this.http.get<any[]>(this.apiUrl);
        }
    }

  updateUserStatus(userId: number, enabled: boolean): Observable<any> {
    return this.http.put(`${this.apiUrl}/${userId}/status?enabled=${enabled}`, {});
  }

  deleteUser(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${userId}`);
  }
}
