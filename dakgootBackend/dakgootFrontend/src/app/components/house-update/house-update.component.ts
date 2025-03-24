import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { HouseService } from '../../services/house-service/house.service';

@Component({
  selector: 'app-house-update',
  templateUrl: './house-update.component.html',
  styleUrls: ['./house-update.component.css']
})
export class HouseUpdateComponent implements OnInit {
  houseForm: FormGroup;
  houseId: string | null | undefined;

  constructor(
    private fb: FormBuilder,
    private houseService: HouseService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.houseForm = this.fb.group({
      gutters: this.fb.array([])
    });
  }

  ngOnInit() {
    this.houseId = this.route.snapshot.paramMap.get('id');
    this.houseService.getHouse(this.houseId).subscribe(data => {
      this.setGutters(data.gutters);
    });
  }

  get gutters(): FormArray {
    return this.houseForm.get('gutters') as FormArray;
  }

  setGutters(gutters: any[]) {
    const gutterFGs = gutters.map(gutter => this.fb.group({
      photoUrl: [gutter.photoUrl, Validators.required],
      comments: [gutter.comments]
    }));
    const gutterFormArray = this.fb.array(gutterFGs);
    this.houseForm.setControl('gutters', gutterFormArray);
  }

  addGutter() {
    this.gutters.push(this.fb.group({
      photoUrl: ['', Validators.required],
      comments: ['']
    }));
  }

  removeGutter(index: number) {
    this.gutters.removeAt(index);
  }

  onSubmit() {
    if (this.houseForm.valid) {
      const formValue = this.houseForm.value;
      const updatedHouse = {
        address: formValue.address,
        resident: {
          name: formValue.residentName,
          email: formValue.residentEmail,
          phone: formValue.residentPhone
        },
        gutters: formValue.gutters.map((gutter: { photoUrl: any; comments: any; }) => ({
          photoUrl: gutter.photoUrl,
          comments: gutter.comments,
          photoAdded: !!gutter.photoUrl
        }))
      };

      this.houseService.updateHouse(this.houseId, updatedHouse).subscribe(() => {
        this.router.navigate(['/']);
      });
    }
  }
}
