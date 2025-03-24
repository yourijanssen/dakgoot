import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HouseListComponent } from './components/house-list-component/house-list.component';
import { HouseDetailComponent } from './components/house-detail-component/house-detail.component';
import {HouseCreateComponent} from "./components/house-create/house-create.component";
import {HouseUpdateComponent} from "./components/house-update/house-update.component";

const routes: Routes = [
  { path: '', component: HouseListComponent },
  { path: 'house/:id', component: HouseDetailComponent },
  { path: 'create-house', component: HouseCreateComponent },
  { path: 'update-house/:id', component: HouseUpdateComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
