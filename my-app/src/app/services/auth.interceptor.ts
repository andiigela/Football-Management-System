import { Injectable } from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor, HttpErrorResponse, HttpClient
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { RefreshTokenRequestDto } from '../common/refresh-token-request-dto';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(
        private http: HttpClient,
        private authService: AuthService,
        private router: Router
    ) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(this.addTokenToRequest(request)).pipe(
            catchError((error: any) => {
                if (error instanceof HttpErrorResponse && error.status === 401 && this.authService.getRefreshToken()) {
                    return this.handle401Error(request, next);
                } else {
                    return throwError(error);
                }
            })
        );
    }

    private addTokenToRequest(request: HttpRequest<any>): HttpRequest<any> {
        const accessToken = this.authService.getAccessToken(); // Check local storage for access token
        if (accessToken) {
            return request.clone({
                setHeaders: {
                    Authorization: `Bearer ${accessToken}`
                }
            });
        }
        return request;
    }

    private handle401Error(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const refreshToken = this.authService.getRefreshToken(); // Using AuthService to retrieve refresh token from local storage
        if (!refreshToken) {
            this.authService.clearTokens(); // Clear tokens if refresh token is not available
            this.router.navigateByUrl('/login');
            return throwError('Refresh token is null');
        }

        const refreshTokenRequestDto = new RefreshTokenRequestDto(refreshToken);
        return this.http.post('http://localhost:8080/api/auth/refreshToken', refreshTokenRequestDto).pipe(
            switchMap((response: any) => {
                if (!response.accessToken) {
                    // If access token is null, clear tokens and redirect to login
                    this.authService.clearTokens();
                    this.router.navigateByUrl('/login');
                    return throwError('Access token is null');
                }
                // Set the new access token in local storage
                this.authService.setAccessToken(response.accessToken);
                const clonedRequest = this.addTokenToRequest(request);
                return next.handle(clonedRequest);
            }),
            catchError((error: any) => {
                if (error instanceof HttpErrorResponse && error.status === 401) {
                    // If refresh token is expired or invalid, clear tokens and redirect to login
                    this.authService.clearTokens();
                    this.router.navigateByUrl('/login');
                }
                return throwError(error);
            })
        );
    }
}
