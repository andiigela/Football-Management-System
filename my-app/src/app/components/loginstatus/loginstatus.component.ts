import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { AuthService } from "../../services/auth.service";

@Component({
  selector: 'app-loginstatus',
  templateUrl: './loginstatus.component.html',
  styleUrls: ['./loginstatus.component.css']
})
export class LoginstatusComponent implements OnInit {
  isAuthenticated: boolean = false;

  constructor(private authService: AuthService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.getAuthenticationStatus();
  }

    getAuthenticationStatus(): void {
        this.authService.isAuthenticated.subscribe((res: boolean) => {
            console.log('Authentication status changed:', res);
            this.isAuthenticated = res;
        });
    }


    logOut(): void {
    this.authService.logout();
        this.cdr.detectChanges();
        this.isAuthenticated = true;
    }
}
