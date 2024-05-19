import {
    ActivatedRouteSnapshot,
    CanActivate,
    Router,
    RouterStateSnapshot,
    UrlTree
} from '@angular/router';
import {Injectable} from "@angular/core";
import {Observable} from 'rxjs';
import {AuthService} from "../services/auth.service";

@Injectable()
export class AuthGuard2 implements CanActivate {
    constructor(private authService: AuthService,private router: Router) {
    }
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        if(localStorage.getItem("refreshToken") == null){
            return this.router.parseUrl('/login');
        } else {
            return true;
        }
        throw new Error('Method not implemented.');
    }

}
