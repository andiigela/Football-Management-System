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
import {PlayersListComponent} from "./components/players-list/players-list.component";
import {CreatePlayerComponent} from "./components/create-player/create-player.component";
import {PlayerEditComponent} from "./components/player-edit/player-edit.component";
import {LeagueComponent} from "./components/league/league.component";
import {MatchComponent} from "./components/match/match.component";
import {TransferComponent} from "./components/transfer/transfer.component";
import {UpdateLeagueComponent} from "./components/update-league-component/update-league-component.component";
import {NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";
import {SeasonComponent} from "./components/season/season.component";
import {EditSeasonComponent} from "./components/edit-season/edit-season.component";
import {CreateSeasonComponent} from "./components/create-season/create-season.component";
import {CreateLeagueComponent} from "./components/create-league/create-league.component";
import {RoundsComponent} from "./components/rounds/rounds.component";
import {CreateRoundComponent} from "./components/create-round/create-round.component";



const routes : Routes = [
  {path: 'login',component:LoginComponent,canActivate: [AuthGuard]},
  {path: 'register', component:RegisterComponent,canActivate: [AuthGuard]},
  {path: 'profile/:id',component:ProfileComponent,canActivate: [AuthGuard2]},
  {path: 'dashboard',component:DashboardComponent,canActivate: [AuthGuard2]},
  {path: 'dashboard/users',component:UsersComponent,canActivate: [AuthGuard2]},
  {path: 'club', component:ClubComponent,canActivate: [AuthGuard2]},
  {path: 'players',component:PlayersListComponent,canActivate: [AuthGuard2]},
  {path: 'create-player',component:CreatePlayerComponent},
  {path: 'players/edit/:id',component:PlayerEditComponent},
  {path: 'league/:id/seasons',component:SeasonComponent},
  {path: 'league/:leagueId/seasons/edit-season/:id', component: EditSeasonComponent },
  {path: 'league/:id/create-season', component: CreateSeasonComponent },
  {path: 'league',component:LeagueComponent,canActivate:[AuthGuard2]},
  {path: 'create-league',component:CreateLeagueComponent,canActivate:[AuthGuard2]},
  {path : 'match',component:MatchComponent,canActivate:[AuthGuard2]},
  {path: 'transfer',component:TransferComponent,canActivate:[AuthGuard2]},
  {path: 'update-league/:id', component:UpdateLeagueComponent },
  {path: 'league/:leagueId/seasons/:seasonId/rounds', component: RoundsComponent },
  {path: 'league/:leagueId/seasons/:seasonId/create-round', component: CreateRoundComponent },
  {path: 'league/:leagueId/seasons/:seasonId/rounds/:roundId/edit-match/:matchId', component: MatchComponent },

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
    ClubComponent,
    CreatePlayerComponent,
    PlayerEditComponent,
    PlayersListComponent,
    LeagueComponent,
    MatchComponent,
    TransferComponent,
    UpdateLeagueComponent,
    SeasonComponent,
    EditSeasonComponent,
    CreateSeasonComponent,
    CreateLeagueComponent,
    RoundsComponent,
    CreateRoundComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    NgbPaginationModule,
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
