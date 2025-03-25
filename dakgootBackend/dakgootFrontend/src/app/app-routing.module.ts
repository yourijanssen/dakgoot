import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HouseListComponent } from './components/house-list-component/house-list.component';
import { HouseDetailComponent } from './components/house-detail-component/house-detail.component';
import {HouseCreateComponent} from "./components/house-create/house-create.component";
import {HouseUpdateComponent} from "./components/house-update/house-update.component";
import {LoginComponent} from "./components/login/login.component";
import {LogoutComponent} from "./components/logout/logout.component";
import {RegisterComponent} from "./components/register/register.component";
import {UserListComponent} from "./components/user-list/user-list.component";
import {AuthGuard} from "./services/auth.guard";

const routes: Routes = [

  {
    path: '',
    component: HouseListComponent,
    canActivate: [AuthGuard] // Protect this route
  },
  { path: 'house/:id', component: HouseDetailComponent },
  { path: 'create-house', component: HouseCreateComponent },
  { path: 'update-house/:id', component: HouseUpdateComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'logout', component: LogoutComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'users', component: UserListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
