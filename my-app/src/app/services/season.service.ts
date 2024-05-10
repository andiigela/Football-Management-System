import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {SeasonDto} from "../common/season-dto";
import {ClubDto} from "../common/club-dto";
import {MatchDto} from "../common/match-dto";


@Injectable({
  providedIn: 'root'
})
export class SeasonService {
  private baseUrl = 'http://localhost:8080/api/seasons';
  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    })
    return headers;
  }
  constructor(private http: HttpClient) { }

  getAllSeasons(): Observable<SeasonDto[]> {
    let headers = this.getHeaders();
    return this.http.get<SeasonDto[]>(`${this.baseUrl}`, {headers});
  }

  getSeasonById(id: number): Observable<SeasonDto> {
    let headers = this.getHeaders();
    return this.http.get<SeasonDto>(`${this.baseUrl}/${id}`,{headers});
  }

  createSeason(leagueId: number, season: SeasonDto): Observable<void> {
    let headers = this.getHeaders();
    return this.http.post<void>(`${this.baseUrl}/save/${leagueId}`, season, {headers});
  }

  updateSeason(id: number, season: SeasonDto): Observable<void> {
    let headers = this.getHeaders();
    return this.http.put<void>(`${this.baseUrl}/update/${id}`, season, {headers});
  }

  deleteSeason(id: number): Observable<void> {
    let headers = this.getHeaders();
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`, {headers});
  }

  createRoundsForSeason(seasonId: number): Observable<void> {
    let headers = this.getHeaders();
    return this.http.post<void>(`${this.baseUrl}/${seasonId}/rounds`, {}, {headers});
  }
}
