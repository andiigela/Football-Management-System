import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { RegisterDto } from '../../common/register-dto';
import { Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  registerDto: RegisterDto = {
    username: '',
    password: '',
    clubName: '',
    email: ''
  };

  confirmPassword: string = '';

  constructor(private authService: AuthService, private router: Router, private formBuilder: FormBuilder) {
    this.registerForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      clubName: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, { validator: this.passwordMatchValidator });
  }

  // Custom validator function to check if passwords match
  passwordMatchValidator(formGroup: FormGroup) {
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;

    if (password !== confirmPassword) {
      formGroup.get('confirmPassword')?.setErrors({ mismatch: true });
    } else {
      formGroup.get('confirmPassword')?.setErrors(null);
    }
  }

  register() {
    if (this.registerForm.invalid) {
      return;
    }
    this.registerDto.email = this.registerForm.get('email')?.value;
    this.registerDto.clubName = this.registerForm.get('clubName')?.value;
    this.registerDto.username = this.registerForm.get('username')?.value;
    this.registerDto.password = this.registerForm.get('password')?.value;

    // Proceed with registration
    this.authService.register(this.registerDto).subscribe(
        response => {
          console.log('Registration successful');
          this.router.navigateByUrl('/login');
        },
        error => {
          console.error('Registration failed:', error);
        }
    );
  }
}
