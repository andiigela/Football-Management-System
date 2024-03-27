import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { RegisterDto } from '../../common/register-dto';
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerDto: RegisterDto = {
    username: '',
    password: '',
    clubName: '',
    email: ''
  };
  confirmPassword: string = '';
  constructor(private authService: AuthService, private router: Router) {}

  register() {
    if (this.registerDto.password !== this.confirmPassword) {
      console.error('Passwords do not match');
      return;
    }
    this.authService.register(this.registerDto).subscribe(
        response => {
          console.log('Registration successful');
          this.router.navigateByUrl('/login');
        },
        error => {
          if (error.error && typeof error.error === 'string') {
            console.error('Registration failed:', error.error);
          } else {
            console.error('Registration failed:', error);
          }
        }
    );
  }

}
