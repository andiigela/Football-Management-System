import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { LoginDto } from "../common/login-dto";
import { BehaviorSubject, Observable, throwError } from "rxjs";
import { Router } from "@angular/router";
import { CookieService } from "ngx-cookie-service";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    isAuthenticated = new BehaviorSubject<boolean>(false);
    private readonly refreshTokenKey = 'refreshToken';
    private readonly accessTokenKey = 'accessToken';

    constructor(private httpClient: HttpClient, private router: Router, private cookieService: CookieService) {
        // Check authentication status on application startup
        this.checkIsAuthenticated();
    }

    loginUser(user: LoginDto): Observable<any> {
        if (user == null) return throwError("User object is null");
        return this.httpClient.post<any>("http://localhost:8080/api/auth/login", user, { withCredentials: true });
    }

    checkIsAuthenticated(): void {
        const refreshToken: string | null = localStorage.getItem(this.refreshTokenKey);
        const isAuthenticated: boolean = !!refreshToken;
        this.isAuthenticated.next(isAuthenticated);
    }

    logout(): void {
        localStorage.removeItem(this.refreshTokenKey);
        localStorage.removeItem(this.accessTokenKey);
        this.isAuthenticated.next(false);
        this.router.navigateByUrl("/login");
    }

    setTokens(accessToken: string, refreshToken: string): void {
        localStorage.setItem(this.accessTokenKey, accessToken);
        localStorage.setItem(this.refreshTokenKey, refreshToken);
        this.isAuthenticated.next(true);
    }

    getAccessToken(): string | null {
        return localStorage.getItem(this.accessTokenKey);
    }

    getRefreshToken(): string | null {
        return localStorage.getItem(this.refreshTokenKey);
    }

    clearTokens(): void {
        this.clearAccessToken();
        this.clearRefreshToken();
    }

    private clearAccessToken(): void {
        this.cookieService.delete('accessToken');
        localStorage.removeItem(this.accessTokenKey);
    }

    private clearRefreshToken(): void {
        localStorage.removeItem(this.refreshTokenKey);
    }

    setAccessToken(accessToken: string): void {
        localStorage.setItem(this.accessTokenKey, accessToken);
    }

}
