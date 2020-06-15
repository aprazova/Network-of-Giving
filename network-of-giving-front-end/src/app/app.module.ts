import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';

import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ClarityModule, ClrFormsModule } from "@clr/angular";
import { HeaderComponent } from './header/header.component';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { NavMenuComponent } from './nav-menu/nav-menu.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage } from 'ngx-webstorage';
import { AddCharityComponent } from './add-charity/add-charity.component';
import { EditorModule } from '@tinymce/tinymce-angular';
import { HttpClientInterceptor } from './http-client-interceptor';
import { CharityComponent } from './charity/charity.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { FileService } from './services/file-service/file.service';
import { ImageNameDirective } from './image-name.directive';
import { LinesRangePipe } from './pipes/lines-range.pipe';
import { CharityFilterPipe } from './pipes/charity-filter.pipe';
import { MatDialogModule } from '@angular/material/dialog';
import { DonateDialogComponent } from './dialogs/donate-dialog/donate-dialog.component';
import { VolunteerDialogComponent } from './dialogs/volunteer-dialog/volunteer-dialog.component';
import { DeleteDialogComponent } from './dialogs/delete-dialog/delete-dialog.component';
import { UserInfoComponent } from './user-info/user-info.component';
import { UserEditComponent } from './user-edit/user-edit.component';
import { CharityEditComponent } from './charity-edit/charity-edit.component';
import { LogOutDialogComponent } from './dialogs/log-out-dialog/log-out-dialog.component';
import { AlreadyVolunteerComponent } from './dialogs/already-volunteer/already-volunteer.component';
import { ShareButtonsModule } from 'ngx-sharebuttons/buttons';
import { ShareIconsModule } from 'ngx-sharebuttons/icons';


@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
    NavMenuComponent,
    AddCharityComponent,
    CharityComponent,
    PageNotFoundComponent,
    ImageNameDirective,
    LinesRangePipe,
    CharityFilterPipe,
    DonateDialogComponent,
    VolunteerDialogComponent,
    DeleteDialogComponent,
    UserInfoComponent,
    UserEditComponent,
    CharityEditComponent,
    LogOutDialogComponent,
    AlreadyVolunteerComponent,
  ],
  imports: [
    BrowserModule,
    ClarityModule,
    BrowserAnimationsModule,
    ClrFormsModule,
    FormsModule,
    ReactiveFormsModule,
    Ng2Webstorage.forRoot(),
    AppRoutingModule,
    HttpClientModule,
    EditorModule,
    MatDialogModule,
    ShareButtonsModule,
    ShareIconsModule
  ],
  providers: [
    { 
      provide: HTTP_INTERCEPTORS, 
      useClass: HttpClientInterceptor, 
      multi: true
    },
    FileService],
  bootstrap: [AppComponent]
})
export class AppModule { }
