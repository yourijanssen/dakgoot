import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MainService } from "../../services/main.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  errorMessage: string = '';

  // Define available roles
  roles = [
    { value: 'HOMEOWNER', label: 'Homeowner' },
    { value: 'ADMIN', label: 'Administrator' },
    { value: 'MAINTENANCE', label: 'Maintenance' }
  ];

  constructor(
    private fb: FormBuilder,
    private mainService: MainService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      name: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(50)
      ]],
      email: ['', [
        Validators.required,
        Validators.email
      ]],
      password: ['', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(50)
      ]],
      phone: ['', [
        Validators.pattern(/^[0-9]{10}$/) // Basic 10-digit phone number validation
      ]],
      role: ['HOMEOWNER', Validators.required] // Default role
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      // Create a copy of the form value to potentially modify phone
      const registrationData = {...this.registerForm.value};

      // Optional: Format phone number if provided
      if (registrationData.phone) {
        // Remove any non-digit characters
        registrationData.phone = registrationData.phone.replace(/\D/g, '');
      }

      this.mainService.register(registrationData).subscribe({
        next: (response) => {
          if (response.success) {
            console.log(response.message);
            this.router.navigate(['/login']);
          } else {
            this.errorMessage = response.message;
          }
        },
        error: (error) => {
          this.errorMessage = error.message || 'Registration failed';
        }
      });
    }
  }

  // Convenience getters for easy access in template
  get name() { return this.registerForm.get('name'); }
  get email() { return this.registerForm.get('email'); }
  get password() { return this.registerForm.get('password'); }
  get phone() { return this.registerForm.get('phone'); }
  get role() { return this.registerForm.get('role'); }
}
