import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MatchDto } from '../common/match-dto';

@Injectable({
  providedIn: 'root'
})
export class RoundsService {

  private baseUrl = 'http://localhost:8080/api/rounds';

  constructor(private http: HttpClient) { }

  getMatchesForRound(roundId: number): Observable<MatchDto[]> {
    return this.http.get<MatchDto[]>(`${this.baseUrl}/${roundId}/matches`);
  }
}
