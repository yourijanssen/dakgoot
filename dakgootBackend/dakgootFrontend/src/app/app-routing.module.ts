import { NgModule, Injectable } from '@angular/core';
import { RouterModule, Routes, Router } from '@angular/router';
import { LoginComponent } from "./components/login/login.component";
import { LogoutComponent } from "./components/logout/logout.component";
import { RegisterComponent } from "./components/register/register.component";
import { AdminDashboardComponent } from "./components/admin-dashboard/admin-dashboard.component";
import { HomeownerDashboardComponent } from "./components/homeowner-dashboard/homeowner-dashboard.component";
import { MainService } from "./services/main.service";
import { CanActivate } from '@angular/router';
import {AuthGuard} from "./services/auth.guard";
import {HouseDetailComponent} from "./components/house-detail/house-detail.component";
import {AddHouseComponent} from "./components/add-house/add-house.component";

@Injectable({
  providedIn: 'root'
})
export class RoleBasedRedirectGuard implements CanActivate {
  constructor(
    private mainService: MainService,
    private router: Router
  ) {}

  canActivate(): boolean {
    const user = this.mainService.getCurrentUser();

    if (user) {
      switch (user.role) {
        case 'ADMIN':
          this.router.navigate(['/admin-dashboard']);
          return false;
        case 'HOMEOWNER':
          this.router.navigate(['/home']);
          return false;
        default:
          this.router.navigate(['/login']);
          return false;
      }
    }

    // Not logged in, allow navigation to login
    this.router.navigate(['/login']);
    return false;
  }
}

const routes: Routes = [
  {
    path: '',
    canActivate: [RoleBasedRedirectGuard],
    component: HomeownerDashboardComponent
  },
  {
    path: 'add-house',
    component: AddHouseComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'house/:id',
    component: HouseDetailComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'logout',
    component: LogoutComponent
  },
  {
    path: 'admin-dashboard',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'home',
    component: HomeownerDashboardComponent,
    canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [RoleBasedRedirectGuard]
})
export class AppRoutingModule { }
