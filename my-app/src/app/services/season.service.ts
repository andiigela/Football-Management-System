import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SeasonDto } from "../common/season-dto";
import { PageResponseDto } from "../common/page-response-dto";

@Injectable({
  providedIn: 'root'
})
export class SeasonService {
  private baseUrl = 'http://localhost:8080/api/seasons';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    });
    return headers;
  }

  createSeason(leagueId: number, seasonDto: SeasonDto): Observable<any> {

    const headers = this.getHeaders();
    return this.http.post(`${this.baseUrl}/${leagueId}/create`, seasonDto, { headers: headers });
  }

  getSeasons(leagueId: number, pageNumber: number, pageSize: number): Observable<PageResponseDto<SeasonDto>> {
    const headers = this.getHeaders();
    return this.http.get<PageResponseDto<SeasonDto>>(`${this.baseUrl}/${leagueId}/?pageNumber=${pageNumber}&pageSize=${pageSize}`, { headers: headers });
  }

  getSeason(leagueId: number, seasonId: number): Observable<SeasonDto> {
    const headers = this.getHeaders();
    return this.http.get<SeasonDto>(`${this.baseUrl}/${leagueId}/${seasonId}`, { headers: headers });
  }

  editSeason(leagueId: number, seasonId: number, seasonDto: SeasonDto): Observable<any> {
    const headers = this.getHeaders();
    return this.http.put(`${this.baseUrl}/${leagueId}/edit/${seasonId}`, seasonDto, { headers: headers });
  }

  deleteSeason(leagueId: number, seasonId: number): Observable<any> {
    const headers = this.getHeaders();
    return this.http.delete(`${this.baseUrl}/${leagueId}/delete/${seasonId}`, { headers: headers });
  }
  createRoundsAndMatches(leagueId: number, seasonId: number): Observable<any> {
  const headers = this.getHeaders();
  return this.http.get(`${this.baseUrl}/${leagueId}/generate/${seasonId}`,{ headers: headers });
  }

}
