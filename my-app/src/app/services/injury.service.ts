import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {PageResponseDto} from "../common/page-response-dto";

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
  public retrieveInjuries(playerId: number,pageNumber: number, pageSize: number): Observable<PageResponseDto>{
    let headers = this.getHeaders();
    return this.http.get<PageResponseDto>(`${this.injuryUrl}/${playerId}/?page=${pageNumber}&size=${pageSize}`,{headers});
  }


}
