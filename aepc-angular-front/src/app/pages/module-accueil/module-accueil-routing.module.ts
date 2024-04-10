import { CompoAccueilComponent } from './compo-accueil/compo-accueil.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authenticationGuardService } from 'src/app/shared/services/auth-guard-service/auth-guard-service.guard';

const routes: Routes = [
  {
    path:'', component: CompoAccueilComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ModuleAccueilRoutingModule { }
