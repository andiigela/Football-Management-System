import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { LoginDto } from "../common/login-dto";
import { BehaviorSubject, Observable, throwError } from "rxjs";
import { Router } from "@angular/router";
import { RegisterDto } from "../common/register-dto";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    isAuthenticated = new BehaviorSubject<boolean>(false);
    private readonly refreshTokenKey = 'refreshToken';
    private readonly accessTokenKey = 'accessToken';
    private readonly accessTokenExpiryKey = 'accessTokenExpiry';
    currentUserEmail = new BehaviorSubject<string | null>(null);
    currentClubId = new BehaviorSubject<number | null>(null);

    constructor(private httpClient: HttpClient, private router: Router) {
        // Check authentication status on application startup
        this.checkIsAuthenticated();
        this.checkTokenExpiration();
        setInterval(() => {
            this.checkTokenExpiration();
        }, 60000);
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
        const userEmail = this.getEmailFromToken();
        this.setUserEmail(userEmail);
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

    private checkTokenExpiration(): void {
        const accessToken: string | null = localStorage.getItem(this.accessTokenKey);
        if (accessToken && this.isTokenExpired(accessToken)) {
           this.clearTokens();
            this.logout();
        }
    }


    setAccessToken(accessToken: string): void {
        localStorage.setItem(this.accessTokenKey, accessToken);
        console.log('Access token updated'); // Add a log to indicate the update
    }
    getRoleFromToken(): string | null {
        const accessToken: string | null = this.getAccessToken();
        if (!accessToken) return null;
        const payload = this.parseJwtPayload(accessToken);
        return payload?.role || null;
    }

    getUserIdFromToken(): number | null {
        const accessToken: string | null = this.getAccessToken();
        if (!accessToken) return null;

        const payload = this.parseJwtPayload(accessToken);
        return payload?.userId || null;
    }

    getEmailFromToken(): string | null {
        const accessToken: string | null = this.getAccessToken();
        if (!accessToken) return null;

        const payload = this.parseJwtPayload(accessToken);
        return payload?.sub || null;
    }

    parseJwtPayload(token: string): any {
        const payloadBase64 = token.split('.')[1];
        const decodedPayload = atob(payloadBase64);
        return JSON.parse(decodedPayload);
    }

    setUserEmail(email: string | null): void {
        this.currentUserEmail.next(email);
    }

    getUserEmail(): Observable<string | null> {
        return this.currentUserEmail.asObservable();
    }

    isTokenExpired(token: string): boolean {
        try {
            const payload: any = this.parseJwtPayload(token);
            const currentTime = Date.now() / 1000;
            return payload.exp < currentTime;
        } catch (error) {
            return true; // Assume token is expired if there is an error in decoding
        }
    }
}
