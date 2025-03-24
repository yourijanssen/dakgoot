import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HouseService } from '../../services/house-service/house.service';

@Component({
  selector: 'app-house-create',
  templateUrl: './house-create.component.html',
  styleUrls: ['./house-create.component.css']
})
export class HouseCreateComponent {
  houseForm: FormGroup;
  formError: string = '';

  constructor(private fb: FormBuilder, private houseService: HouseService) {
    this.houseForm = this.fb.group({
      address: ['', Validators.required],
      residentName: ['', Validators.required],
      residentEmail: ['', [Validators.required, Validators.email]],
      residentPhone: ['', Validators.required],
      gutterPhotoUrl: [''],
      gutterComments: ['']
    });
  }

  onSubmit() {
    if (this.houseForm.valid) {
      const formValue = this.houseForm.value;
      const newHouse = {
        address: formValue.address,
        resident: {
          name: formValue.residentName,
          email: formValue.residentEmail,
          phone: formValue.residentPhone
        },
        gutters: [{
          photoUrl: formValue.gutterPhotoUrl,
          comments: formValue.gutterComments,
          photoAdded: !!formValue.gutterPhotoUrl
        }]
      };

      this.houseService.createHouse(newHouse).subscribe(response => {
        console.log('House created:', response);
        this.houseForm.reset();
        this.formError = '';
      });
    } else {
      this.formError = 'Please correct the errors in the form.';
    }
  }
}
