import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../../services/auth-service/auth-service.service";


@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})


export class LogoutComponent implements OnInit {
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.performLogout();
  }

  performLogout() {
    this.authService.logout().subscribe({
      next: (response) => {
        // Logout successful, navigate to login page
        this.router.navigate(['/login']);
      },
      error: (error) => {
        // Handle logout error
        this.errorMessage = error.message || 'Logout failed';
        console.error('Logout error', error);

        // Even if there's an error, try to clear local user data
        this.authService.clearCurrentUser();
        this.router.navigate(['/login']);
      }
    });
  }
}
