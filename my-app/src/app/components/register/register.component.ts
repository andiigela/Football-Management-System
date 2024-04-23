import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterDto } from '../../common/register-dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  registerDto: RegisterDto = {
    email: '',
    password: '',
    clubName: ''
  };

  confirmPassword: string = '';

  constructor(private authService: AuthService, private formBuilder: FormBuilder, private router: Router) {
    this.registerForm = this.formBuilder.group({
      clubName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, { validator: this.passwordMatchValidator });
  }

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
    this.registerDto.password = this.registerForm.get('password')?.value;
    this.registerDto.clubName = this.registerForm.get('clubName')?.value;

    this.authService.register(this.registerDto).subscribe(
      response => {
        console.log('Registration successful');
        this.router.navigateByUrl('/login');
      },
      error => {
        if (error.status === 401) {
          // Handle the case where the email is already taken
          console.error('Registration failed:', 'Email is taken!');
          // Set an error state in your form or display an error message
          this.registerForm.get('email')?.setErrors({ emailTaken: true });
        } else {
          console.error('Registration failed:', error); // Log the error response
        }
      }
    );
  }


}


