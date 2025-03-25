import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { AuthService, User } from '../../services/auth-service/auth-service.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  errorMessage: string = '';
  successMessage: string = '';
  isLoading: boolean = false;

  constructor(
    private userService: AuthService,
    private location: Location
  ) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.isLoading = true;
    this.clearMessages();
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = error.message;
        this.isLoading = false;
      }
    });
  }

  deleteUser(userId: number | undefined) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.clearMessages();
      this.userService.deleteUser(userId).subscribe({
        next: (response) => {
          // Remove user from local list
          this.users = this.users.filter(user => user.id !== userId);

          // Set success message
          this.successMessage = response.message || 'User deleted successfully';
        },
        error: (error) => {
          this.errorMessage = error.message;
        }
      });
    }
  }

  goBack() {
    this.location.back();
  }

  // Clear both error and success messages
  private clearMessages() {
    this.errorMessage = '';
    this.successMessage = '';
  }
}
