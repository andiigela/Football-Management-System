import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { UsersComponent } from './users/users.component';
import {NavbarComponent} from "./navbar/navbar.component";
import {CommonModule} from "@angular/common";
import {LoginstatusComponent} from "../loginstatus/loginstatus.component";


const routes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
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
