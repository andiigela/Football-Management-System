import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {LeagueDto} from "../common/league-dto";
import {SeasonDto} from "../common/season-dto";
import {PageResponseDto} from "../common/page-response-dto";

@Injectable({
  providedIn: 'root'
})
export class LeagueService {
  private leagueUrl = `${environment.api.baseUrl + environment.api.leagueUrl}`
  constructor(private http:HttpClient) { }
  returnAllLeagues(pageNumber: number, pageSize: number):Observable<PageResponseDto<LeagueDto>> {
    console.log(this.leagueUrl)
    return this.http.get<PageResponseDto<LeagueDto>>(`${this.leagueUrl}?pageNumber=${pageNumber}&pageSize=${pageSize}`);
  }

  createLeague(leagueDTO: LeagueDto): Observable<any> {
    return this.http.post<any>(`${this.leagueUrl}`, leagueDTO);
  }

  returnLeagueById(id: number): Observable<LeagueDto> {
    return this.http.get<LeagueDto>(`${this.leagueUrl}/${id}`);
  }

  deleteLeague(id: number): Observable<any> {
    return this.http.delete<any>(`${this.leagueUrl}/${id}`);
  }

  editLeague(id: number, leagueDTO: LeagueDto): Observable<any> {
    return this.http.put<any>(`${this.leagueUrl}/${id}`, leagueDTO);
  }

  getSeasonsForLeague(leagueId: number): Observable<SeasonDto[]> {
    return this.http.get<SeasonDto[]>(`${this.leagueUrl}/${leagueId}/seasons`);
  }
}
