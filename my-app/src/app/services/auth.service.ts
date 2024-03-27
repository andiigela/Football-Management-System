import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { LoginDto } from "../common/login-dto";
import { BehaviorSubject, Observable, throwError } from "rxjs";
import { Router } from "@angular/router";
import {RegisterDto} from "../common/register-dto";


@Injectable({
    providedIn: 'root'
})
export class AuthService {

    isAuthenticated = new BehaviorSubject<boolean>(false);
    private readonly refreshTokenKey = 'refreshToken';
    private readonly accessTokenKey = 'accessToken';

    constructor(private httpClient: HttpClient, private router: Router) {
        // Check authentication status on application startup
        this.checkIsAuthenticated();
    }

    loginUser(user: LoginDto): Observable<any> {
        if (user == null) return throwError("User object is null");
        return this.httpClient.post<any>("http://localhost:8080/api/auth/login", user, { withCredentials: true });
    }

    register(user: RegisterDto): Observable<any> {
        if (user == null) return throwError("User object is null");
        return this.httpClient.post<any>("http://localhost:8080/api/auth/register", user, { withCredentials: true });
    }

    checkIsAuthenticated(): void {
        const refreshToken: string | null = localStorage.getItem(this.refreshTokenKey);
        const isAuthenticated: boolean = !!refreshToken;
        console.log('Authentication Status:', isAuthenticated);
        this.isAuthenticated.next(isAuthenticated);
    }


    logout(): void {
        localStorage.removeItem(this.refreshTokenKey);
        localStorage.removeItem(this.accessTokenKey);
        console.log('Logged out');
        this.isAuthenticated.next(false);
        this.router.navigateByUrl("/login");
    }

    setTokens(accessToken: string, refreshToken: string): void {
        localStorage.setItem(this.accessTokenKey, accessToken);
        localStorage.setItem(this.refreshTokenKey, refreshToken);
        console.log('Tokens set');
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
        localStorage.removeItem(this.accessTokenKey);
    }

    private clearRefreshToken(): void {
        localStorage.removeItem(this.refreshTokenKey);
    }

    setAccessToken(accessToken: string): void {
        localStorage.setItem(this.accessTokenKey, accessToken);
    }

}
