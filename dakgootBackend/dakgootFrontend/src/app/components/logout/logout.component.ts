import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import {MainService} from "../../services/main.service";


@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})


export class LogoutComponent implements OnInit {
  errorMessage: string = '';

  constructor(
    private mainService: MainService,
    private router: Router
  ) {}

  ngOnInit() {
    this.performLogout();
  }

  performLogout() {
    this.mainService.logout().subscribe({
      next: (response) => {
        // Logout successful, navigate to login page
        this.router.navigate(['/login']);
      },
      error: (error) => {
        // Handle logout error
        this.errorMessage = error.message || 'Logout failed';
        console.error('Logout error', error);

        // Even if there's an error, try to clear local user data
        this.mainService.clearCurrentUser();
        this.router.navigate(['/login']);
      }
    });
  }
}
