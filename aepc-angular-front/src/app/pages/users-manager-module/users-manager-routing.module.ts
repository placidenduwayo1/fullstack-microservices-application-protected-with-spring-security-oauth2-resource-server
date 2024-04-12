import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserManagerComponent } from './user-manager/user-manager.component';
import { UserUPrintComponent } from './user-manager/user-u-print/user-u-print.component';
import { UserCreateComponent } from './user-manager/user-create/user-create.component';
import { usersRouteResolve } from 'src/app/shared/services/route-resolve-services/users-resolve/route.resolve';

const routes: Routes = [
  {
    path: '',
    component: UserManagerComponent, 
    resolve:{
      getAllUsers: usersRouteResolve
    }
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
