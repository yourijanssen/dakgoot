import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './components/AppComponent/app.component';
import {RouterLink, RouterOutlet} from "@angular/router";
import {AppRoutingModule, RoleBasedRedirectGuard} from "./app-routing.module";
import {ReactiveFormsModule} from "@angular/forms";
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { RegisterComponent } from './components/register/register.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { HomeownerDashboardComponent } from './components/homeowner-dashboard/homeowner-dashboard.component';
import {AuthGuard} from "./services/auth.guard";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LogoutComponent,
    RegisterComponent,
    AdminDashboardComponent,
    HomeownerDashboardComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterOutlet,
    AppRoutingModule,
    RouterLink,
    ReactiveFormsModule
  ],
  providers: [    AuthGuard,
    RoleBasedRedirectGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
