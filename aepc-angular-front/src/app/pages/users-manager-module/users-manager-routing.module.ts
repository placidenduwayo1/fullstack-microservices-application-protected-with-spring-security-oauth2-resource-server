import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserManagerComponent } from './user-manager/user-manager.component';
import { UserUPrintComponent } from './user-manager/user-u-print/user-u-print.component';
import { UserCreateComponent } from './user-manager/user-create/user-create.component';

const routes: Routes = [
  {
    path: '',
    component: UserManagerComponent
  },
  {
    path: 'user-u', component: UserUPrintComponent
  },
  {
    path: 'user-create', component: UserCreateComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersManagerRoutingModule { }
