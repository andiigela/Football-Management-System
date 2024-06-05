import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { LoginDto } from "../../common/login-dto";
import { Router } from "@angular/router";
import { AuthService } from "../../services/auth.service";
import { SharedNotificationService } from "../../services/shared-notification.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginFormGroup: FormGroup = new FormGroup<any>({});
  loginError: boolean = false;
  ERRORMSG: string = '';

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private authService: AuthService,
              private sharedNotificationService: SharedNotificationService) { }

  ngOnInit(): void {
    this.authService.checkIsAuthenticated();
    this.loginFormGroup = this.formBuilder.group({
      login: this.formBuilder.group({
        email: ['', [Validators.required, Validators.email]],
        password: new FormControl('', [Validators.required])
      })
    });
  }

  loginButton(): void {
    this.loginFormGroup.markAllAsTouched();
    this.ERRORMSG = '';
    const email: string = this.loginFormGroup.controls['login'].get('email')?.value;
    const password: string = this.loginFormGroup.controls['login'].get('password')?.value;

    const loginDto = new LoginDto(email, password);

    if (email != '' && password != '') {
      this.authService.loginUser(loginDto).subscribe((res: any) => {
        if (res.accessToken) {
          const payload = this.authService.parseJwtPayload(res.accessToken);
          const isEnabled = payload.enabled;

          if (isEnabled) {
            const role = payload.role;
            if (role === 'ADMIN_LEAGUE' || role === 'ADMIN_CLUB') {
              this.authService.setTokens(res.accessToken, res.refreshToken);
              this.router.navigateByUrl("/dashboard");
              // this.sharedNotificationService.readNotificationsFromWebSocket();
            } else {
              console.log("Unknown role:", role);
            }
          } else {
            this.loginError = true;
            this.ERRORMSG = 'User is not enabled. Please contact support.';
          }
        } else {
          this.loginError = true;
          this.ERRORMSG = 'An unexpected error occurred. Please try again later.';
        }
      }, (error => {
        console.log("Error:", error); // Log the error here
        if (error.status === 401) {
          this.loginError = true;
          this.ERRORMSG = 'Email or password is incorrect';
        } else {
          this.loginError = true;
          this.ERRORMSG = 'Email or password is incorrect';
        }
      }));
    }
  }
}
