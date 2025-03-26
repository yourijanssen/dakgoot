import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  MainService,
  House,
  RepairRequest,
  RepairRequestStatus
} from '../../services/main.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-house-detail',
  templateUrl: './house-detail.component.html',
  styleUrls: ['./house-detail.component.css']
})
export class HouseDetailComponent implements OnInit {
  house: House | null = null;
  repairRequests: RepairRequest[] = [];



  // Form for creating new repair requests
  repairRequestForm: FormGroup;

  // Statuses for dropdown
  repairRequestStatuses = Object.values(RepairRequestStatus);

  // Loading and error states
  isLoading = false;
  errorMessage = '';

  // Edit mode for repair requests
  editingRepairRequest: RepairRequest | null = null;

  constructor(
    private route: ActivatedRoute,
    private mainService: MainService,
    private router: Router,
    private fb: FormBuilder
  ) {
    // Initialize the form
    this.repairRequestForm = this.fb.group({
      description: ['', [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(500)
      ]],
      repairType: [''],
      status: [RepairRequestStatus.PENDING]
    });
  }

  ngOnInit() {
    // Get house ID from route
    const houseId = this.route.snapshot.paramMap.get('id');

    if (houseId) {
      this.loadHouseDetails(+houseId);
      this.loadRepairRequests(+houseId);
    }
  }

  loadHouseDetails(houseId: number) {
    this.isLoading = true;
    this.mainService.getHouse(houseId).subscribe({
      next: (house) => {
        this.house = house;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load house details';
        this.isLoading = false;
      }
    });
  }

  loadRepairRequests(houseId: number) {
    this.mainService.getHouseRepairRequests(houseId).subscribe({
      next: (requests) => {
        this.repairRequests = requests;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load repair requests';
      }
    });
  }
  // In the component class
  formatStatus(status: RepairRequestStatus): string {
    // Convert from SNAKE_CASE to Title Case
    return status
      .toLowerCase()
      .split('_')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');
  }

  createRepairRequest() {
    if (this.repairRequestForm.valid && this.house) {
      const newRequest: RepairRequest = {
        ...this.repairRequestForm.value,
        houseId: this.house.id
      };

      this.mainService.createRepairRequest(this.house.id!, newRequest).subscribe({
        next: (createdRequest) => {
          this.repairRequests.push(createdRequest);
          this.repairRequestForm.reset({
            description: '',
            repairType: '',
            status: RepairRequestStatus.PENDING
          });
        },
        error: (error) => {
          this.errorMessage = 'Failed to create repair request';
        }
      });
    }
  }

  editRepairRequest(request: RepairRequest) {
    this.editingRepairRequest = { ...request };
  }

  updateRepairRequest() {
    if (this.editingRepairRequest && this.house) {
      this.mainService.updateRepairRequest(this.editingRepairRequest).subscribe({
        next: (updatedRequest) => {
          const index = this.repairRequests.findIndex(r => r.id === updatedRequest.id);
          if (index !== -1) {
            this.repairRequests[index] = updatedRequest;
          }
          this.editingRepairRequest = null;
        },
        error: (error) => {
          this.errorMessage = 'Failed to update repair request';
        }
      });
    }
  }

  deleteRepairRequest(requestId?: number) {
    if (requestId) {
      this.mainService.deleteRepairRequest(requestId).subscribe({
        next: () => {
          this.repairRequests = this.repairRequests.filter(r => r.id !== requestId);
        },
        error: (error) => {
          this.errorMessage = 'Failed to delete repair request';
        }
      });
    }
  }

  cancelEdit() {
    this.editingRepairRequest = null;
  }
}
