import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {House, MainService} from "../../services/main.service";

@Component({
  selector: 'app-homeowner-dashboard',
  templateUrl: './homeowner-dashboard.component.html',
  styleUrls: ['./homeowner-dashboard.component.css']
})
export class HomeownerDashboardComponent implements OnInit {
  houses: House[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(
    private mainService: MainService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadHouses();
  }

  loadHouses() {
    this.isLoading = true;
    this.errorMessage = '';

    this.mainService.getHouses().subscribe({
      next: (houses) => {
        // Map houses and calculate repair request counts
        this.houses = houses.map(house => ({
          ...house,
          repairRequestCount: this.getRepairRequestCount(house)
        }));
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading houses', error);
        this.errorMessage = 'Failed to load houses. Please try again.';
        this.isLoading = false;
      }
    });
  }

  getRepairRequestCount(house: House): number {
    // This method would ideally fetch or calculate repair request count
    // For now, return 0 as a placeholder
    return 0;
  }

  viewHouseDetails(houseId?: number) {
    if (houseId) {
      this.router.navigate(['/house', houseId]);
    }
  }

  addNewHouse() {
    // Navigate to add house form or open a modal
    this.router.navigate(['/add-house']);
  }

  // Optional: Method to refresh houses
  refreshHouses() {
    this.loadHouses();
  }

  deleteHouse(houseId?: number) {
    // Confirm deletion
    const confirmDelete = confirm('Are you sure you want to delete this house?');

    if (confirmDelete && houseId) {
      this.mainService.deleteHouse(houseId).subscribe({
        next: (response) => {
          // Remove the house from the local list
          this.houses = this.houses.filter(house => house.id !== houseId);

          // Optional: Show success message
          // You could use a toast or snackbar for this
          console.log(response.message);
        },
        error: (error) => {
          // Handle error
          this.errorMessage = error.message || 'Failed to delete house';
        }
      });
    }
  }
}
