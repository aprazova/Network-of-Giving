import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { HomePageComponent } from './home-page/home-page.component';
import { AddCharityComponent } from './add-charity/add-charity.component';
import { CharityComponent } from './charity/charity.component';
import {AuthGuard} from './auth.guard';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { UserInfoComponent } from './user-info/user-info.component';
import { UserEditComponent } from './user-edit/user-edit.component';
import { CharityEditComponent } from './charity-edit/charity-edit.component';

const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'information', component: UserInfoComponent,canActivate: [AuthGuard]},
    {path: 'edit', component: UserEditComponent,  canActivate: [AuthGuard]},
    {path: 'edit/charity/:id', component: CharityEditComponent,  canActivate: [AuthGuard]},
    {path: 'register', component: RegisterComponent},
    {path: 'login', component: LoginComponent},
    {path: 'home', component: HomePageComponent},
    {path: 'add-charity', component: AddCharityComponent, canActivate: [AuthGuard]},
    {path: 'charity/:id', component: CharityComponent},
    {path: '**', component: PageNotFoundComponent}
    

  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
