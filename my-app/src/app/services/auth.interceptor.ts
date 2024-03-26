import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse, HttpClient
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';
import { RefreshTokenRequestDto } from '../common/refresh-token-request-dto';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private http: HttpClient, private cookieService: CookieService, private router: Router) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(this.addTokenToRequest(request)).pipe(
      catchError((error: any) => {
        if (error instanceof HttpErrorResponse && error.status === 401 && this.cookieService.get('refreshToken')) {
          return this.handle401Error(request, next);
        } else {
          return throwError(error);
        }
      })
    );
  }

  private addTokenToRequest(request: HttpRequest<any>): HttpRequest<any> {
    const accessToken = this.cookieService.get('accessToken');
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
    const refreshTokenRequestDto = new RefreshTokenRequestDto(this.cookieService.get('refreshToken'));
    return this.http.post('http://localhost:8080/api/auth/refreshToken', refreshTokenRequestDto).pipe(
      switchMap((response: any) => {
        if (!response.accessToken) {
          // If access token is null, redirect to login
          this.cookieService.delete('accessToken');
          this.cookieService.delete('refreshToken');
          this.router.navigateByUrl('/login');
          return throwError('Access token is null');
        }
        // Set the new access token
        this.cookieService.set('accessToken', response.accessToken);
        const clonedRequest = this.addTokenToRequest(request);
        return next.handle(clonedRequest);
      }),
      catchError((error: any) => {
        if (error instanceof HttpErrorResponse && error.status === 401) {
          // If refresh token is expired or invalid, delete both tokens and redirect to login
          this.cookieService.delete('accessToken');
          this.cookieService.delete('refreshToken');
          this.router.navigateByUrl('/login');
        }
        return throwError(error);
      })
    );
  }
}
