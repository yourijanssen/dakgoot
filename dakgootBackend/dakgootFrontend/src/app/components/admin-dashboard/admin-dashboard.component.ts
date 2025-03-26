import { Component, OnInit } from '@angular/core';
import { MainService, User, House, RepairRequest } from '../../services/main.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  users: User[] = [];
  houses: House[] = [];
  repairRequests: RepairRequest[] = []; // Initialize as empty array

  // Dashboard statistics
  userCount = 0;
  houseCount = 0;
  repairRequestCount = 0;
  pendingRepairRequests = 0;

  // Loading and error states
  isLoading = false;
  errorMessage = '';

  constructor(private mainService: MainService) {}

  ngOnInit() {
    this.loadDashboardData();
  }

  loadDashboardData() {
    this.isLoading = true;
    this.errorMessage = '';

    // Parallel data loading
    Promise.all([
      this.loadUsers(),
      this.loadHouses(),
      this.loadRepairRequests()
    ]).then(() => {
      this.calculateStatistics();
      this.isLoading = false;
    }).catch(error => {
      this.errorMessage = 'Failed to load dashboard data';
      this.isLoading = false;
      console.error(error);
    });
  }

  private async loadUsers() {
    try {
      this.users = await this.mainService.getAllUsers().toPromise() || [];
      this.userCount = this.users.length;
    } catch (error) {
      console.error('Error loading users', error);
      this.users = []; // Ensure it's an array
    }
  }

  private async loadHouses() {
    try {
      // Use the new management endpoint
      this.houses = await this.mainService.getHouseManagement().toPromise() || [];
      this.houseCount = this.houses.length;
    } catch (error) {
      console.error('Error loading houses', error);
      this.houses = [];
    }
  }

  private async loadRepairRequests() {
    try {
      // Use a method that returns all repair requests for admin
      const allRepairRequests = await this.mainService.getAllRepairRequests().toPromise();

      // Ensure repairRequests is always an array
      this.repairRequests = allRepairRequests || [];
      this.repairRequestCount = this.repairRequests.length;
    } catch (error) {
      console.error('Error loading repair requests', error);
      this.repairRequests = []; // Ensure it's an array
    }
  }

  private calculateStatistics() {
    this.pendingRepairRequests = this.repairRequests.filter(
      request => request.status === 'PENDING'
    ).length;
  }

  // Action methods
  deleteUser(userId: number | undefined) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.mainService.deleteUser(userId).subscribe({
        next: () => {
          // Remove user from local list
          this.users = this.users.filter(user => user.id !== userId);
          this.userCount--;
        },
        error: (error) => {
          this.errorMessage = error.message;
        }
      });
    }
  }

  getUserEmail(userId: number | undefined): string {
    const user = this.users.find(u => u.id === userId);
    return user ? user.email : 'N/A';
  }

  getHouseAddress(houseId: number | undefined): string {
    const house = this.houses.find(h => h.id === houseId);
    return house ? house.address : 'N/A';
  }

  getOwnerName(houseId: number | undefined): string {
    const house = this.houses.find(h => h.id === houseId);
    // @ts-ignore
    return <string><unknown>house ? house.ownerName : 'N/A';
  }

  getRepairRequestsForHouse(houseId: number | undefined): RepairRequest[] {
    return this.repairRequests.filter(request => request.houseId === houseId);
  }
  // Action methods
  deleteHouse(houseId: number | undefined) {
    if (confirm('Are you sure you want to delete this house?')) {
      this.mainService.deleteHouse(houseId).subscribe({
        next: () => {
          // Remove house from local list
          this.houses = this.houses.filter(house => house.id !== houseId);
          this.houseCount--;

          // Remove associated repair requests
          this.repairRequests = this.repairRequests.filter(
            request => request.houseId !== houseId
          );
          this.calculateStatistics();
        },
        error: (error) => {
          this.errorMessage = error.message;
        }
      });
    }
  }


}
