import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router
} from '@angular/router';
import { MainService } from './main.service';

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
          // Admin can access admin dashboard and all routes
          if (state.url === '/admin-dashboard' ||
            state.url.startsWith('/add-house') ||
            state.url.startsWith('/house/')) {
            return true;
          }
          this.router.navigate(['/admin-dashboard']);
          return false;

        case 'HOMEOWNER':
          // Homeowner can access home dashboard, add-house, and house details
          const allowedRoutes = [
            '/home',
            '/add-house',
            '/house/',
            '/logout'
          ];

          const isAllowedRoute = allowedRoutes.some(route =>
            state.url === route || state.url.startsWith(route)
          );

          if (isAllowedRoute) {
            return true;
          }

          // If not an allowed route, redirect to home
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
