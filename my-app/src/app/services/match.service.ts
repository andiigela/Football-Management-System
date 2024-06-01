import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { MatchDto } from '../common/match-dto';

@Injectable({
    providedIn: 'root'
})
export class MatchService {
    private readonly apiUrl: string = `${environment.api.baseUrl}${environment.api.matchUrl}`;

    constructor(private http: HttpClient) { }

    // Create a match
    createMatch(roundId: number, matchDto: MatchDto): Observable<any> {
        return this.http.post(`${this.apiUrl}/${roundId}/create`, matchDto);
    }

    // Get all matches for a round with pagination
    getMatches(roundId: number, page: number = 0, size: number = 10): Observable<any> {
        return this.http.get(`${this.apiUrl}/${roundId}/?page=${page}&size=${size}`);
    }

    // Get a match by its ID
    getMatch(roundId: number, matchId: number): Observable<any> {
        return this.http.get(`${this.apiUrl}/${roundId}/${matchId}`);
    }

    // Update a match
    editMatch(roundId: number, matchId: number, matchDto: MatchDto): Observable<any> {
        return this.http.put(`${this.apiUrl}/${roundId}/edit/${matchId}`, matchDto);
    }

    // Delete a match
    deleteMatch(roundId: number, matchId: number): Observable<any> {
        return this.http.delete(`${this.apiUrl}/${roundId}/delete/${matchId}`);
    }

  filterMatches(date?: string, homeTeamResult?: number, awayTeamResult?: number, page: number = 0, size: number = 10): Observable<any> {
    let params = new HttpParams();

    if (date) {
      params = params.set('date', date);
    }
    if (homeTeamResult !== undefined) {
      params = params.set('homeTeamResult', homeTeamResult.toString());
    }
    if (awayTeamResult !== undefined) {
      params = params.set('awayTeamResult', awayTeamResult.toString());
    }
    params = params.set('pageNumber', page.toString());
    params = params.set('pageSize', size.toString());

    return this.http.get(`${this.apiUrl}/filter`, { params });
  }
}
