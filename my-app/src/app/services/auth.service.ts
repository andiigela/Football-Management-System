import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginDto} from "../common/login-dto";
import {BehaviorSubject, Observable, throwError} from "rxjs";
import {CookieService} from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isAuthenticated = new BehaviorSubject<boolean>(false);
  constructor(private httpClient: HttpClient,private cookieService: CookieService) { }

  loginUser(user: LoginDto): Observable<any>{
    if(user == null) return throwError("User object is null");
    return this.httpClient.post<any>("http://localhost:8080/api/auth/login",user,{withCredentials: true});
  }
   checkIsAuthenticated(): void {
     const isAuthenticated = !!this.cookieService.get('refreshToken');
     this.isAuthenticated.next(isAuthenticated);
   }
   logout(): void {
      if(this.cookieService.get("accessToken") != null){
        this.cookieService.delete("accessToken")
       }
      if(this.cookieService.get("refreshToken") != null){
        this.cookieService.delete("refreshToken")
      }
      this.isAuthenticated.next(false);
   }
}
