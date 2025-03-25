import { Component, OnInit } from '@angular/core';
import { HouseService } from "../../services/house-service/house.service";
import { AuthService } from "../../services/auth-service/auth-service.service";

@Component({
  selector: 'app-house-list',
  templateUrl: './house-list.component.html',
  styleUrls: ['./house-list.component.css']
})
export class HouseListComponent implements OnInit {
  houses: any[] = [];
  currentUser: any = null;

  constructor(
    private houseService: HouseService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    // Get current user
    this.currentUser = this.authService.getCurrentUser();
    this.loadHouses();
  }

  loadHouses() {
    this.houseService.getHouses().subscribe(data => {
      this.houses = data;
    });
  }

  deleteHouse(id: number) {
    // Only allow deletion for admin or manager
    if (this.canDeleteHouse()) {
      this.houseService.deleteHouse(id).subscribe(() => {
        this.loadHouses(); // Refresh the list after deletion
      });
    }
  }

  /**
   * Check if the current user can delete houses
   */
  canDeleteHouse(): boolean {
    return this.currentUser &&
      (this.currentUser.role === 'ADMIN' ||
        this.currentUser.role === 'MANAGER');
  }

  /**
   * Check if the current user can manage accounts
   */
  canManageAccounts(): boolean {
    return this.currentUser &&
      this.currentUser.role === 'ADMIN';
  }

  /**
   * Check if the current user can create houses
   */
  canCreateHouse(): boolean {
    return this.currentUser &&
      (this.currentUser.role === 'ADMIN' ||
        this.currentUser.role === 'MANAGER' ||
        this.currentUser.role === 'USER');
  }
}
