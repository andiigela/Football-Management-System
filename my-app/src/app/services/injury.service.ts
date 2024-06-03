import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {PageResponseDto} from "../common/page-response-dto";
import {PlayerDto} from "../common/player-dto";
import {InjuryDto} from "../common/injury-dto";

@Injectable({
  providedIn: 'root'
})
export class InjuryService {
  injuryUrl: string = "http://localhost:8080/api/injuries"
  constructor(private http: HttpClient) {
  }
  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    })
    return headers;
  }
  public retrieveInjuries(playerId: number,pageNumber: number, pageSize: number): Observable<PageResponseDto<InjuryDto>>{
    let headers = this.getHeaders();
    return this.http.get<PageResponseDto<InjuryDto>>(`${this.injuryUrl}/${playerId}/?pageNumber=${pageNumber}&pageSize=${pageSize}`,{headers});
  }
  public createInjury(playerId: number,injuryDto: InjuryDto): Observable<any>{
    let headers = this.getHeaders();
    return this.http.post(`${this.injuryUrl}/${playerId}/create`,injuryDto,{headers});
  }
  public retrieveInjury(playerId: number,injuryId: number): Observable<any>{
    let headers = this.getHeaders();
    return this.http.get(`${this.injuryUrl}/${playerId}/${injuryId}`,{headers}) // retrieving the injury for edit
  }
  public editInjury(playerId: number,injuryDto: InjuryDto): Observable<any>{
    let headers = this.getHeaders();
    return this.http.put(`${this.injuryUrl}/${playerId}/edit/${injuryDto.id}`,injuryDto,{headers});
  }
  public deleteInjury(playerId: number,injuryId: number): Observable<any>{
    let headers = this.getHeaders();
    return this.http.delete(`${this.injuryUrl}/${playerId}/delete/${injuryId}`,{headers});
  }
}
