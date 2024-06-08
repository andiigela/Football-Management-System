import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {LeagueDto} from "../common/league-dto";
import {StandingDTO} from "../common/standing-dto";
import {ClubDto} from "../common/club-dto";

@Injectable({
  providedIn: 'root'
})
export class StandingService {
  private standingUrl = ''

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    });
  }


  constructor(private http:HttpClient) { }
  returnStandingById(id: number): Observable<LeagueDto> {
    return this.http.get<LeagueDto>(`${this.standingUrl}/${id}`);
  }
  returnStandingsBySeasonId(id: number): Observable<LeagueDto> {
    return this.http.get<LeagueDto>(`${this.standingUrl}/seasons/${id}`);
  }

  createStandings(season_id:number,clubs:ClubDto[]): Observable<any> {
    let headers = this.getHeaders();
    return this.http.post<any>(`http://localhost:8080/api/v1/standing/season/${season_id}`, clubs,{headers});
  }

  editLeague(standingDto:StandingDTO): Observable<any> {
    let headers= this.getHeaders();

    return this.http.put<any>(`${this.standingUrl}/${standingDto.id}`,{headers});
  }

}
