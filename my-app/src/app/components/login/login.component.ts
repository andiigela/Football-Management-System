import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {LoginDto} from "../../common/login-dto";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginFormGroup: FormGroup = new FormGroup<any>('');
  constructor(private formBuilder: FormBuilder,private http: HttpClient,private router: Router,
              private cookieService: CookieService,private authService: AuthService) {

  }

  ngOnInit(): void {
    this.loginFormGroup = this.formBuilder.group({
      login: this.formBuilder.group({
        username: new FormControl(''),
        password: new FormControl('')
      })
    });
    }
    loginButton(): void {
      let username:string = this.loginFormGroup.controls['login'].get('username')?.value;
      let password:string = this.loginFormGroup.controls['login'].get('password')?.value;
      let loginDto = new LoginDto(username,password);
      this.authService.loginUser(loginDto).subscribe((res:any) => {
        document.cookie = `accessToken=${res.accessToken}`
        document.cookie = `refreshToken=${res.refreshToken}`
        this.router.navigateByUrl("/dashboard")
        this.authService.checkIsAuthenticated();
      })
    }
}
