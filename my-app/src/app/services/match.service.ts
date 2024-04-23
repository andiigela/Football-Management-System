import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {MatchDto} from "../common/match-dto";

@Injectable({
  providedIn: 'root'
})
export class MatchService {
  private readonly transferUrl : string = `${environment.api.baseUrl + environment.api.matchUrl}}`


  constructor(private http:HttpClient) { }

  listAllMatches():Observable<MatchDto[]>{
    return this.http.get<MatchDto[]>(`${this.transferUrl}`)
  }
  getMatchById(id:number):Observable<MatchDto>{
    return this.http.get<MatchDto>(`${this.transferUrl}/${id}`)
  }
  deleteMatch(id:number):Observable<any>{
    return this.http.delete<MatchDto>(`${this.transferUrl}/${id}`)
  }
  editMatch(id:number,matchDTO:MatchDto):Observable<any>{
    return this.http.put<any>(`${this.transferUrl}/${id}`,id)
  }
  createMatch(matchDto:MatchDto):Observable<any>{
    return this.http.post<any>(`${this.transferUrl}`,matchDto)
  }


}
