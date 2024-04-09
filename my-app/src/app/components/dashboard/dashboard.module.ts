import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { UsersComponent } from './users/users.component';
import {NavbarComponent} from "./navbar/navbar.component";
import {CommonModule} from "@angular/common";
import {AuthGuard2} from "../../services/auth2.guard";


const routes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard2],
    children: [
      { path: 'users', component: UsersComponent },

    ]
  },

];

@NgModule({
  declarations: [

    NavbarComponent,
    UsersComponent
  ],
  imports: [
    CommonModule,
      RouterModule.forChild(routes)
  ],
  exports: [
    NavbarComponent
  ]
})
export class DashboardModule { }
