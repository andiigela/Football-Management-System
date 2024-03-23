import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse, HttpClient
} from '@angular/common/http';
import {catchError, Observable, switchMap, throwError} from 'rxjs';
import {CookieService} from "ngx-cookie-service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {


  constructor(private http: HttpClient,private cookieService: CookieService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const clonedReq = request.clone({
      setHeaders: {
        Authorization: `Bearer ${this.cookieService.get("accessToken")}`
      }
    });
    return next.handle(clonedReq).pipe(catchError((err: HttpErrorResponse ) => {
      if(err.status == 401){
        const newClonedReq = request.clone({
          setHeaders: {
            Authorization: `Bearer ${this.cookieService.get("refreshToken")}`
          }
        })
        return next.handle(newClonedReq)
      }
      return throwError(()=>err)
    }));
    }



}

