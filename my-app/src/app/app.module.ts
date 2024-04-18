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
import { MatchComponent } from './components/match/match.component';
import { TransferComponent } from './components/transfer/transfer.component';
import {LeagueComponent} from "./components/league/league.component";
import {UpdateLeagueComponent} from "./components/update-league-component/update-league-component.component";

const routes : Routes = [
  {path: 'login',component:LoginComponent,canActivate: [AuthGuard]},
  {path: 'dashboard',component:DashboardComponent,canActivate: [AuthGuard2]},
  {path: 'register', component:RegisterComponent,canActivate: [AuthGuard]},
  {path: 'league',component:LeagueComponent,canActivate:[AuthGuard2]},
  {path : 'match',component:MatchComponent,canActivate:[AuthGuard2]},
  {path: 'transfer',component:TransferComponent,canActivate:[AuthGuard2]},
  { path: 'update-league/:id', component:UpdateLeagueComponent }, // Route for updating league data with a parameter for league ID


]


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    LoginstatusComponent,
    RegisterComponent,
    LeagueComponent,
    MatchComponent,
    TransferComponent,
    UpdateLeagueComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
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
