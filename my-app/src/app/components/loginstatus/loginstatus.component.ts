import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-loginstatus',
  templateUrl: './loginstatus.component.html',
  styleUrls: ['./loginstatus.component.css']
})
export class LoginstatusComponent implements OnInit {
  isAuthenticated: boolean = false;
  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.getAuthenticationStatus();
  }
  getAuthenticationStatus(){
    this.authService.isAuthenticated.subscribe((res:boolean) => {
      this.isAuthenticated=res;
      this.authService.checkIsAuthenticated();
    });
  }
  logOut(){
    this.authService.logout();

  }


}
