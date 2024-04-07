import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import { LoginDto } from "../../common/login-dto";
import { Router } from "@angular/router";
import { AuthService } from "../../services/auth.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    loginFormGroup: FormGroup = new FormGroup<any>({});
    loginError: boolean = false; // Flag to indicate login error
  constructor(private formBuilder: FormBuilder, private http: HttpClient, private router: Router, private authService: AuthService) {}

    ngOnInit(): void {
      this.authService.checkIsAuthenticated();
      this.loginFormGroup = this.formBuilder.group({
            login: this.formBuilder.group({
              email: ['', [Validators.required, Validators.email]],
              password: new FormControl('',[Validators.required])
            })
        });
    }

    loginButton(): void {
        this.loginFormGroup.markAllAsTouched();
        const email: string = this.loginFormGroup.controls['login'].get('email')?.value;
        const password: string = this.loginFormGroup.controls['login'].get('password')?.value;

        const loginDto = new LoginDto(email, password);

        if(email != '' && password != ''){
            this.authService.loginUser(loginDto).subscribe((res: any) => {
                    this.authService.setTokens(res.accessToken, res.refreshToken);
                    this.router.navigateByUrl("/dashboard");
                },(error => {
                  if(error.status == 401){
                      this.loginError = true;
                  }

                })
            );
        }

    }
}
