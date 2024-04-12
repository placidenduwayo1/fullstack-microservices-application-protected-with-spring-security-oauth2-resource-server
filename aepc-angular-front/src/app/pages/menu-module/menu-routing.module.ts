import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MenuComponent } from './menu/menu.component';
import { authenticationGuardService } from 'src/app/shared/services/auth-guard-service/auth-guard-service.guard';

const routes: Routes = [
  {
    path:'session/menu', 
    component: MenuComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyMenuRoutingModule { }
