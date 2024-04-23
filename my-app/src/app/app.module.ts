import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import {RouterModule, Routes} from "@angular/router";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { DashboardComponent } from './components/dashboard/dashboard.component';
import {AuthInterceptor} from "./services/auth.interceptor";
import { LoginstatusComponent } from './components/loginstatus/loginstatus.component';
import {AuthGuard} from "./services/auth.guard";
import {AuthGuard2} from "./services/auth2.guard";
import {RegisterComponent} from "./components/register/register.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {UsersComponent} from "./components/users/users.component";
import {ClubComponent} from "./components/club/club.component";


const routes : Routes = [
  {path: 'login',component:LoginComponent,canActivate: [AuthGuard]},
  {path: 'register', component:RegisterComponent,canActivate: [AuthGuard]},
  {path: 'profile',component:ProfileComponent,canActivate: [AuthGuard2]},
  {path: 'dashboard',component:DashboardComponent,canActivate: [AuthGuard2]},
  {path: 'dashboard/users',component:UsersComponent,canActivate: [AuthGuard2]},
  {path: 'club', component:ClubComponent,canActivate: [AuthGuard2]},
  //{path: 'players',component:PlayerComponent,canActivate: [AuthGuard2]},
  //{path: 'create-player',component:CreatePlayerComponent},
  //{path: 'players/edit/:id',component:PlayerEditComponent},
  //{path: 'league',component:LeagueComponent,canActivate:[AuthGuard2]},
  //{path : 'match',component:MatchComponent,canActivate:[AuthGuard2]},
  //{path: 'transfer',component:TransferComponent,canActivate:[AuthGuard2]},
  //{path: 'update-league/:id', component:UpdateLeagueComponent },
]


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoginstatusComponent,
    RegisterComponent,
    DashboardComponent,
    ProfileComponent,
    UsersComponent,
      ClubComponent

  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,

  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    AuthGuard,
    AuthGuard2
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
