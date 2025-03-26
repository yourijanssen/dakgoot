// auth.guard.ts
import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router
} from '@angular/router';
import { MainService } from '../services/main.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private mainService: MainService,
    private router: Router
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    // Check if user is logged in
    if (this.mainService.isLoggedIn()) {
      const user = this.mainService.getCurrentUser();

      // Ensure user exists
      if (!user) {
        this.router.navigate(['/login']);
        return false;
      }

      // Role-based access control
      switch (user.role) {
        case 'ADMIN':
          // Admin can access admin dashboard
          if (state.url === '/admin-dashboard') {
            return true;
          }
          this.router.navigate(['/admin-dashboard']);
          return false;

        case 'HOMEOWNER':
          // Homeowner can access home dashboard
          if (state.url === '/home') {
            return true;
          }
          this.router.navigate(['/home']);
          return false;

        default:
          // Unknown role
          this.router.navigate(['/login']);
          return false;
      }
    }

    // Not logged in, redirect to login
    this.router.navigate(['/login']);
    return false;
  }
}
