import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {UserDto} from "../common/user-dto";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';
  constructor(private http: HttpClient) { }
  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    })
    return headers;
  }
  // getUsers(currentUserId: number | null): Observable<any[]> {
  //   if (currentUserId !== null) {
  //     return this.http.get<any[]>(`${this.apiUrl}?currentUserId=${currentUserId}&isDeleted=false`);
  //   } else {
  //     console.log("User ID not available.");
  //     return this.http.get<any[]>(this.apiUrl);
  //   }
  // }
  getUsersWithUserRole(): Observable<UserDto[]> {
    const headers = this.getHeaders();
    return this.http.get<UserDto[]>(`${this.apiUrl}`, { headers });
  }

  updateUserStatus(userId: number, enabled: boolean): Observable<any> {
    return this.http.put(`${this.apiUrl}/${userId}/status?enabled=${enabled}`, {});
  }

  deleteUser(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${userId}`);
  }

  getUserProfile(userId: number): Observable<any> {
    let headers = this.getHeaders();
    console.log('Headers:', headers);
    return this.http.get(`${this.apiUrl}/${userId}`,{headers});
  }

  updateUser(updatedUserDto: UserDto,file: File|null): Observable<any> {
    let headers = this.getHeaders();
    console.log('Headers:', headers);
    console.log(file);
    console.log(updatedUserDto);
    const formData = new FormData();

    if(file != null){
      formData.append("file",file!);
    }
    formData.append("updatedUserDto",JSON.stringify(updatedUserDto));
    console.log(formData.get("file"));
    return this.http.post(`${this.apiUrl}/update/${updatedUserDto.userId}`, formData,{headers});
  }

  getClubData(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${userId}/club`);
  }

  public getImageUrl(profile_picture: string): Observable<any>{
    let headers = this.getHeaders();
    return this.http.get(`http://localhost:8080/images/${profile_picture}`,{headers,responseType: 'blob'});
  }
}
