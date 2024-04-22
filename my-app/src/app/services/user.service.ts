import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';
  constructor(private http: HttpClient) { }
  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    })
    return headers;
  }
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

  getUserProfile(userId: number): Observable<any> {
    let headers = this.getHeaders();
    return this.http.get(`${this.apiUrl}/${userId}`,{headers});
  }

  updateUser(userId: number, userData: any): Observable<any> {
    let headers = this.getHeaders();
    return this.http.put(`${this.apiUrl}/update/${userId}`, userData,{headers});
  }

  getClubData(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${userId}/club`);
  }
}
