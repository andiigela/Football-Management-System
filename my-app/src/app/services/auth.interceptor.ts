import { Injectable } from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor,
    HttpErrorResponse,
    HttpClient
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { RefreshTokenRequestDto } from '../common/refresh-token-request-dto';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    private isRefreshing = false;

    constructor(
        private http: HttpClient,
        private authService: AuthService,
        private router: Router
    ) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(this.addTokenToRequest(request)).pipe(
            catchError((error: any) => {
                if (error instanceof HttpErrorResponse && error.status === 401) {
                    return this.handle401Error(request, next);
                } else {
                    return throwError(error);
                }
            })
        );
    }

    private addTokenToRequest(request: HttpRequest<any>): HttpRequest<any> {
        const accessToken = this.authService.getAccessToken();
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
        if (this.isRefreshing) {
            // If already refreshing, block other calls until it's done
            return throwError('Token is already refreshing');
        }

        const refreshToken = this.authService.getRefreshToken();
        if (!refreshToken) {
            this.logoutUser();
            return throwError('Refresh token is null');
        }

        const accessToken = this.authService.getAccessToken();
        if (!accessToken || this.authService.isTokenExpired(accessToken)) {
            // Access token expired, proceed with refreshing
            this.isRefreshing = true;
            const refreshTokenRequestDto = new RefreshTokenRequestDto(refreshToken);

            return this.http.post('http://localhost:8080/api/auth/refreshToken', refreshTokenRequestDto).pipe(
                switchMap((response: any) => {
                    this.isRefreshing = false;
                    if (!response.accessToken) {
                        this.logoutUser();
                        return throwError('New access token is null');
                    }
                    this.authService.setAccessToken(response.accessToken);
                    const clonedRequest = this.addTokenToRequest(request);
                    return next.handle(clonedRequest);
                }),
                catchError((error: any) => {
                    this.isRefreshing = false;
                    this.logoutUser();
                    return throwError(error);
                })
            );
        } else {
            // Access token is still valid, logout user
            this.logoutUser();
            return throwError('Access token is not expired');
        }
    }


    private logoutUser(): void {
        this.authService.clearTokens();
        this.router.navigateByUrl('/login');
    }
}
