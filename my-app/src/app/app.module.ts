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
import { CreatePlayerComponent } from './components/create-player/create-player.component';
import { PlayersListComponent } from './components/players-list/players-list.component';
import { PlayerEditComponent } from './components/player-edit/player-edit.component';
import {NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";
const routes : Routes = [
  {path: 'players/edit/:id',component:PlayerEditComponent},
  {path: 'players',component:PlayersListComponent},
  {path: 'create-player',component:CreatePlayerComponent},
  {path: 'login',component:LoginComponent,canActivate: [AuthGuard]},
  {path: 'dashboard',component:DashboardComponent,canActivate: [AuthGuard2]},
  {path: 'register', component:RegisterComponent,canActivate: [AuthGuard]}
]


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    LoginstatusComponent,
    RegisterComponent,
    CreatePlayerComponent,
    PlayersListComponent,
    PlayerEditComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    NgbPaginationModule
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
