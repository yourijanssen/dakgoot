import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './components/AppComponent/app.component';
import { HouseListComponent } from './components/house-list-component/house-list.component';
import { HouseDetailComponent } from './components/house-detail-component/house-detail.component';
import { ResidentComponent } from './components/resident-component/resident.component';
import {RouterLink, RouterOutlet} from "@angular/router";
import {AppRoutingModule} from "./app-routing.module";
import { HouseCreateComponent } from './components/house-create/house-create.component';
import {ReactiveFormsModule} from "@angular/forms";
import { HouseUpdateComponent } from './components/house-update/house-update.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { RegisterComponent } from './components/register/register.component';
import { UserListComponent } from './components/user-list/user-list.component';

@NgModule({
  declarations: [
    AppComponent,
    HouseListComponent,
    HouseDetailComponent,
    ResidentComponent,
    HouseCreateComponent,
    HouseUpdateComponent,
    LoginComponent,
    LogoutComponent,
    RegisterComponent,
    UserListComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterOutlet,
    AppRoutingModule,
    RouterLink,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
