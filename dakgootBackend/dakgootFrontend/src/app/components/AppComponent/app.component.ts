import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {MainService} from "../../services/main.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(
    private mainService: MainService,
    private router: Router
  ) {
    // Check user role on app initialization
    this.checkUserRole();
  }

  get isLoggedIn(): boolean {
    return this.mainService.isLoggedIn();
  }

  get currentUser() {
    return this.mainService.getCurrentUser();
  }

  private checkUserRole() {
    if (this.isLoggedIn) {
      const user = this.currentUser;
      switch (user?.role) {
        case 'ADMIN':
          this.router.navigate(['/admin-dashboard']);
          break;
        case 'HOMEOWNER':
          this.router.navigate(['/home']);
          break;
        default:
          this.router.navigate(['/login']);
      }
    }
  }
}
