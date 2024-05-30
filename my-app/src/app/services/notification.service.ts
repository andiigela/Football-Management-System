import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {PageResponseDto} from "../common/page-response-dto";
import {NotificationDto} from "../common/notification-dto";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notificationUrl: string = "http://localhost:8080/api/notifications"
  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    })
    return headers;
  }
  constructor(private http: HttpClient) { }
  public retrieveNotifications(page: number, size: number): Observable<any>{
    let headers = this.getHeaders();
    return this.http.get(`${this.notificationUrl}/?page=${page}&size=${size}`,{headers});
  }
  public retrieveNotificationsCount(): Observable<any>{
    let headers = this.getHeaders();
    return this.http.get(`${this.notificationUrl}/counts`,{headers});
  }
  public updateNotificationsCount(notificationsCount: number): Observable<any>{
    let headers = this.getHeaders();
    return this.http.put(`${this.notificationUrl}/counts/update`,notificationsCount,{headers});
  }
  public deleteNotification(userId: number,notificationDto: NotificationDto): Observable<any>{
    let headers = this.getHeaders();
      let options = {
          headers: headers,
          body: notificationDto
      };
    return this.http.delete(`${this.notificationUrl}/${userId}/delete`,options);
  }
}
