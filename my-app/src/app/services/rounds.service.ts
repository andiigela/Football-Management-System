import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RoundDto } from '../common/round-dto';

@Injectable({
  providedIn: 'root'
})
export class RoundsService {

  private baseUrl = 'http://localhost:8080/api/rounds';

  constructor(private http: HttpClient) { }

  // Create a round
  createRound(seasonId: number, roundDto: RoundDto): Observable<any> {
    return this.http.post(`${this.baseUrl}/${seasonId}/create`, roundDto);
  }

  // Get all rounds for a season with pagination
  getRounds(seasonId: number, page: number = 0, size: number = 10): Observable<any> {
    return this.http.get(`${this.baseUrl}/${seasonId}/?page=${page}&size=${size}`);
  }

  // Get a round by its ID
  getRound(seasonId: number, roundId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${seasonId}/${roundId}`);
  }

  // Update a round
  editRound(seasonId: number, roundId: number, roundDto: RoundDto): Observable<any> {
    return this.http.put(`${this.baseUrl}/${seasonId}/edit/${roundId}`, roundDto);
  }

  // Delete a round
  deleteRound(seasonId: number, roundId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${seasonId}/delete/${roundId}`);
  }
}
