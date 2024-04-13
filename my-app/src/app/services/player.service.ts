import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {PlayerDto} from "../common/player-dto";
import {PageResponseDto} from "../common/page-response-dto";

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  playerUrl: string = "http://localhost:8080/api/players"
  constructor(private http: HttpClient) { }
  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    })
    return headers;
  }
  public createPlayer(playerDto: PlayerDto): Observable<any>{
    let headers = this.getHeaders();
    return this.http.post(`${this.playerUrl}/create`,playerDto,{headers});
  }
  public retrievePlayers(pageNumber: string, pageSize: string): Observable<PageResponseDto>{
    let headers = this.getHeaders();
    const params = new HttpParams()
      .set('page', pageNumber)
      .set('size', pageSize);
    return this.http.get<PageResponseDto>(`${this.playerUrl}/?page=${pageNumber}&size=${pageSize}`,{headers});
  }
  public retrievePlayer(id: number): Observable<PlayerDto>{
    let headers = this.getHeaders();
    return this.http.get<PlayerDto>(`${this.playerUrl}/${id}`,{headers})
  }
  public updatePlayer(playerDto: PlayerDto,id: number): Observable<any>{
    let headers= this.getHeaders();
    return this.http.post(`${this.playerUrl}/edit/${id}`,playerDto,{headers});
  }
  public deletePlayer(id: number): Observable<any>{
    let headers = this.getHeaders();
    return this.http.delete(`${this.playerUrl}/delete/${id}`,{headers})
  }

}
