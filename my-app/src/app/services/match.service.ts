import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
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
}
