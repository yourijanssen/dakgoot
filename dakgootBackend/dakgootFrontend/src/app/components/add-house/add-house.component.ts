import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { MainService } from '../../services/main.service';
import { House } from '../../services/main.service';

@Component({
  selector: 'app-add-house',
  templateUrl: './add-house.component.html',
  styleUrls: ['./add-house.component.css']
})
export class AddHouseComponent implements OnInit {
  // Use non-null assertion
  houseForm!: FormGroup;
  errorMessage: string = '';
  isSubmitting: boolean = false;

  constructor(
    private fb: FormBuilder,
    private mainService: MainService,
    private router: Router
  ) {}

  ngOnInit() {
    // Initialize form in ngOnInit
    this.createForm();
  }

  private createForm() {
    this.houseForm = this.fb.group({
      address: ['', [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(100),
        this.addressFormatValidator
      ]]
    });
  }

  // Convenience getter for easy access to form fields
  get address() {
    return this.houseForm.get('address');
  }

  // Custom address validator
  private addressFormatValidator(control: AbstractControl): {[key: string]: any} | null {
    const addressPattern = /^(\d+\s+)?[a-zA-Z0-9\s,'-]*$/;
    const valid = addressPattern.test(control.value);
    return valid ? null : {'invalidAddress': {value: control.value}};
  }

  onSubmit() {
    // Mark all fields as touched to trigger validation display
    this.houseForm.markAllAsTouched();

    if (this.houseForm.valid) {
      // Clear any previous error messages
      this.errorMessage = '';
      this.isSubmitting = true;

      // Prepare house data
      const houseData: House = {
        address: this.houseForm.value.address
      };

      // Call service method to create house
      this.mainService.createHouse(houseData).subscribe({
        next: (newHouse) => {
          // Navigate back to home/dashboard after successful creation
          this.router.navigate(['/home']);
          this.isSubmitting = false;
        },
        error: (error) => {
          // Handle error
          this.errorMessage = error.message || 'Failed to add house';
          this.isSubmitting = false;
        }
      });
    }
  }

  // Method to handle cancellation
  onCancel() {
    this.router.navigate(['/home']);
  }
}
