import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { RegistrationComponent } from './registration/registration.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { EmailConfirmationComponent } from './email-confirmation/email-confirmation.component';
import { EmailConfirmationErrorComponent } from './email-confirmation-error/email-confirmation-error.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { LoginComponent } from './login/login.component';
import { AuthInterceptor } from './authentication/auth.interceptor';
import { NewMagazineComponent } from './new-magazine/new-magazine.component';
import { TaskListComponent } from './task-list/task-list.component';
import { TaskComponent } from './task/task.component';
import { MagazineListComponent } from './magazine-list/magazine-list.component';
import { UsersPaneComponent } from './users-pane/users-pane.component';
import { NewUserComponent } from './new-user/new-user.component';
import { NewPaperComponent } from './new-paper/new-paper.component';
import { SuccessPageComponent } from './success-page/success-page.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { FailPageComponent } from './fail-page/fail-page.component';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    NavBarComponent,
    RegistrationComponent,
    EmailConfirmationComponent,
    EmailConfirmationErrorComponent,
    NotFoundComponent,
    LoginComponent,
    NewMagazineComponent,
    TaskListComponent,
    TaskComponent,
    MagazineListComponent,
    UsersPaneComponent,
    NewUserComponent,
    NewPaperComponent,
    SuccessPageComponent,
    ErrorPageComponent,
    FailPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
