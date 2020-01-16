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
    path: 'task/:id',
    component: TaskComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'magazine/new',
    component: NewMagazineComponent,
    canActivate: [RoleGuardService],
    data: { role: 'ROLE_EDITOR'}
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
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
