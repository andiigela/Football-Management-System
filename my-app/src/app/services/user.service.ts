import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import { User } from "../common/user";
import {catchError} from "rxjs/operators";
import {AuthService} from "./auth.service";
import {PageResponseDto} from "../common/page-response-dto";
import {RoundDto} from "../common/round-dto";
import {UserDTO} from "../common/user-dto";
import {LeagueDto} from "../common/league-dto";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiUrl = 'http://localhost:8080/api/users';

    constructor(private http: HttpClient, private authService: AuthService) { }

    private getHeaders(): HttpHeaders {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
        });
        return headers;
    }

    getUsersWithUserRole(pageNumber: number, pageSize: number): Observable<PageResponseDto<UserDTO>> {
        const headers = this.getHeaders();
        return this.http.get<PageResponseDto<UserDTO>>(`${this.apiUrl}?pageNumber=${pageNumber}&pageSize=${pageSize}`, { headers });
    }

    updateUserStatus(userId: number, enabled: boolean): Observable<UserDTO> {
        return this.http.put<UserDTO>(`${this.apiUrl}/${userId}/status?enabled=${enabled}`, {});
    }

    deleteUser(userId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/delete/${userId}`);
    }

    getUserProfile(userId: number): Observable<any> {
        let headers = this.getHeaders();
        console.log('Headers:', headers);
        return this.http.get(`${this.apiUrl}/${userId}`, { headers });
    }

    // updateUser(updatedUserDto: User, file: File | null): Observable<any> {
    //     let headers = this.getHeaders();
    //     const userId = this.authService.getUserIdFromToken(); // Get the user ID from AuthService
    //     console.log('Headers:', headers);
    //     console.log(file);
    //     console.log(updatedUserDto);
    //     const formData = new FormData();
    //     if (file) {
    //         formData.append("file", file);
    //     }
    //     formData.append("updatedUserDto", JSON.stringify(updatedUserDto));
    //
    //     return this.http.post(`${this.apiUrl}/update/${userId}`, formData, { headers }).pipe(
    //         catchError(error => throwError(error))
    //     );
    // }
  updateUser(userId: number, userData: any): Observable<any> {
    let headers = this.getHeaders();
    return this.http.put(`${this.apiUrl}/update/${userId}`, userData,{headers});
  }
    getClubData(userId: number): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/${userId}/club`);
    }

    public getImageUrl(profile_picture: string): Observable<any>{
        let headers = this.getHeaders();
        return this.http.get(`http://localhost:8080/images/${profile_picture}`, { headers, responseType: 'blob' });
    }

  searchUsersByEmail(email: string): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.apiUrl}/search?email=${email}`);
  }
}
