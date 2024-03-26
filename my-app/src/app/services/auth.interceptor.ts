import { Injectable } from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor, HttpErrorResponse, HttpClient, HttpContext
} from '@angular/common/http';
import {catchError, Observable, switchMap, throwError} from 'rxjs';
import {CookieService} from "ngx-cookie-service";
import {RefreshTokenRequestDto} from "../common/refresh-token-request-dto";
import {jwtDecode} from "jwt-decode";
import {Router} from "@angular/router"; // Import jwt_decode library

@Injectable()
export class AuthInterceptor implements HttpInterceptor {


    constructor(private http: HttpClient,private cookieService: CookieService,private router: Router) {}

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        const clonedReq = request.clone({
            setHeaders: {
                Authorization: `Bearer ${this.cookieService.get("accessToken")}`
            }
        });
        return next.handle(clonedReq).pipe(catchError((err: HttpErrorResponse ) => {
            if(err.status == 401 && this.cookieService.get("refreshToken")){
                let refreshTokenRequestDto = new RefreshTokenRequestDto(this.cookieService.get("refreshToken"));

                return this.http.post("http://localhost:8080/api/auth/refreshToken",refreshTokenRequestDto).pipe(
                    switchMap((response: any)=>{
                        this.cookieService.set("accessToken", response.accessToken);
                        this.cookieService.delete("refreshToken");
                        const clonedRequest = request.clone({
                            setHeaders: {
                                Authorization: `Bearer ${response.accessToken}`
                            }
                        });
                        return next.handle(clonedRequest)
                    })
                );


            }
            else if(!this.cookieService.get("refreshToken")){
                this.cookieService.delete("accessToken");
                this.router.navigateByUrl("/login");
            }
            return throwError(()=>err)
        }));
    }



}

