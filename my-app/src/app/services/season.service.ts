import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {SeasonDto} from "../common/season-dto";
import {ClubDto} from "../common/club-dto";
import {MatchDto} from "../common/match-dto";


@Injectable({
  providedIn: 'root'
})
export class SeasonService {
  private baseUrl = 'http://localhost:8080/api/seasons';

  constructor(private http: HttpClient) { }

  getAllSeasons(): Observable<SeasonDto[]> {
    return this.http.get<SeasonDto[]>(`${this.baseUrl}`);
  }

  getSeasonById(id: number): Observable<SeasonDto> {
    return this.http.get<SeasonDto>(`${this.baseUrl}/${id}`);
  }

  createSeason(leagueId: number, season: SeasonDto): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/save/${leagueId}`, season);
  }


  updateSeason(id: number, season: SeasonDto): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/update/${id}`, season);
  }

  deleteSeason(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }



}
