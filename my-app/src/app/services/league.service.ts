import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {LeagueDto} from "../common/league-dto";

@Injectable({
  providedIn: 'root'
})
export class LeagueService {
  private leagueUrl = `${environment.api.baseUrl + environment.api.leagueUrl}`
  constructor(private http:HttpClient) { }
  returnAllLeagues(): Observable<LeagueDto[]> {
    console.log(this.leagueUrl)
    return this.http.get<LeagueDto[]>(`${this.leagueUrl}`);
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
}
