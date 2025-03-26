import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MainService, UserRole } from "../../services/main.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private mainService: MainService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [
        Validators.required,
        Validators.email
      ]],
      password: ['', [
        Validators.required,
        Validators.minLength(6),
      ]]
    });
  }

  ngOnInit() {
    // Check if user is already logged in
    if (this.mainService.isLoggedIn()) {
      this.navigateBasedOnRole();
    }
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.loginForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  onSubmit() {
    // Mark all fields as touched to trigger validation display
    this.loginForm.markAllAsTouched();

    if (this.loginForm.valid) {
      // Clear any previous error messages
      this.errorMessage = '';

      this.mainService.login(this.loginForm.value).subscribe({
        next: (response) => {
          if (response.success) {
            this.navigateBasedOnRole();
          } else {
            this.errorMessage = response.message;
          }
        },
        error: (error) => {
          this.errorMessage = error.message || 'Login failed';
        }
      });
    }
  }

  private navigateBasedOnRole() {
    const user = this.mainService.getCurrentUser();

    if (user) {
      switch (user.role) {
        case UserRole.ADMIN:
          this.router.navigate(['/admin-dashboard']);
          break;
        case UserRole.HOMEOWNER:
          this.router.navigate(['/home']);
          break;
        case UserRole.MAINTENANCE:
          // Add specific routing for maintenance role if needed
          this.router.navigate(['/']);
          break;
        default:
          this.router.navigate(['/login']);
      }
    }
  }
}
