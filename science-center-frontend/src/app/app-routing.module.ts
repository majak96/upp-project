import { MagazineListComponent } from './magazine-list/magazine-list.component';
import { TaskComponent } from './task/task.component';
import { AuthGuardService } from './authentication/auth-guard.service';
import { TaskListComponent } from './task-list/task-list.component';
import { NewMagazineComponent } from './new-magazine/new-magazine.component';
import { NonAuthGuardService } from './authentication/non-auth-guard.service';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { RegistrationComponent } from './registration/registration.component';
import { HomePageComponent } from './home-page/home-page.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmailConfirmationComponent } from './email-confirmation/email-confirmation.component';
import { EmailConfirmationErrorComponent } from './email-confirmation-error/email-confirmation-error.component';
import { RoleGuardService } from './authentication/role-guard.service';
import { UsersPaneComponent } from './users-pane/users-pane.component';
import { NewUserComponent } from './new-user/new-user.component';
import { NewPaperComponent } from './new-paper/new-paper.component';
import { SuccessPageComponent } from './success-page/success-page.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { FailPageComponent } from './fail-page/fail-page.component';


const routes: Routes = [
  {
    path: '',
    component: HomePageComponent
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [NonAuthGuardService]
  },
  {
    path: 'register',
    component: RegistrationComponent,
    canActivate: [NonAuthGuardService]
  },
  {
    path: 'tasks',
    component: TaskListComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'magazines',
    component: MagazineListComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'users',
    component: UsersPaneComponent,
    canActivate: [RoleGuardService],
    data: {role: 'ROLE_ADMINISTRATOR'}
  },
  {
    path: 'user/new',
    component: NewUserComponent,
    canActivate: [RoleGuardService],
    data: {role: 'ROLE_ADMINISTRATOR'}
  },
  {
    path: 'paper/new',
    component: NewPaperComponent,
    canActivate: [RoleGuardService],
    data: {role: 'ROLE_AUTHOR'}
  },
  {
    path: 'task/:id',
    component: TaskComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'magazine/new',
    component: NewMagazineComponent,
    canActivate: [RoleGuardService],
    data: {role: 'ROLE_EDITOR'}
  },
  {
    path: 'emailconfirmation',
    component: EmailConfirmationComponent
  },
  {
    path: 'emailconfirmationerror',
    component: EmailConfirmationErrorComponent
  },
  {
    path: 'paymentsuccess',
    component: SuccessPageComponent
  },
  {
    path: 'paymenterror',
    component: ErrorPageComponent
  },
  {
    path: 'paymentfail',
    component: FailPageComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
