import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {Injectable} from "@angular/core";
import {Observable} from 'rxjs';
import {AuthService} from "../services/auth.service";

@Injectable()
export class AdminLeagueGuard implements CanActivate {
  constructor(private authService: AuthService,private router: Router) {
  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    const role = this.authService.getRoleFromToken()
    if(role === "ADMIN_LEAGUE"){
      return true;
    }
    return this.router.parseUrl("/");
  }

}
