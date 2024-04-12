import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { UsersComponent } from './users/users.component';
import { NavbarComponent } from "./navbar/navbar.component";
import { CommonModule, DatePipe } from "@angular/common"; // Import DatePipe
import { AuthGuard2 } from "../../services/auth2.guard";
import { ProfileComponent } from './profile/profile.component';
import { ReactiveFormsModule } from '@angular/forms';

const routes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard2],
    children: [
      { path: 'users', component: UsersComponent },
      { path: 'profile', component: ProfileComponent },
    ]
  },
];

@NgModule({
  declarations: [
    NavbarComponent,
    UsersComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule
  ],
  providers: [DatePipe],
  exports: [
    NavbarComponent
  ]
})
export class DashboardModule { }
