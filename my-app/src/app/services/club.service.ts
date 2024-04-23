// club.service.ts

import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClubDto } from '../common/club-dto';

@Injectable({
  providedIn: 'root'
})
export class ClubService {
  private apiUrl = 'http://localhost:8080/api/clubs';

  constructor(private http: HttpClient) {}
  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    })
    return headers;
  }
  updateClub(clubId: number, clubData: Partial<ClubDto>): Observable<any> {
    let headers = this.getHeaders().set('Content-Type', 'application/json');
    const url = `${this.apiUrl}/edit/${clubId}`;
    return this.http.put(url, JSON.stringify(clubData), { headers: headers });
  }
  getClubById(clubId: number): Observable<ClubDto> {
    let headers = this.getHeaders();
    const url = `${this.apiUrl}/${clubId}`;
    return this.http.get<ClubDto>(url);
  }

  getAllClubs(): Observable<ClubDto[]> {
    let headers = this.getHeaders();
    return this.http.get<ClubDto[]>(this.apiUrl);
  }

  deleteClub(clubId: number): Observable<any> {
    let headers = this.getHeaders();
    const url = `${this.apiUrl}/delete/${clubId}`;
    return this.http.delete(url);
  }

  // getClubIdByUserId(userId: number): Observable<number> {
  //   let headers = this.getHeaders();
  //   const url = `${this.apiUrl}/clubId/${userId}`;
  //   return this.http.get<number>(url);
  // }
  getClubDataByUserId(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${userId}/club`);
  }
}
