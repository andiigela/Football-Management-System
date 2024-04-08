import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {PlayerDto} from "../common/player-dto";

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
  public retrievePlayers(): Observable<PlayerDto[]>{
    let headers = this.getHeaders();
    return this.http.get<PlayerDto[]>(`${this.playerUrl}/`,{headers});
  }
  public retrievePlayer(id: number): Observable<PlayerDto>{
    let headers = this.getHeaders();
    return this.http.get<PlayerDto>(`${this.playerUrl}/edit/${id}`,{headers})
  }

}
