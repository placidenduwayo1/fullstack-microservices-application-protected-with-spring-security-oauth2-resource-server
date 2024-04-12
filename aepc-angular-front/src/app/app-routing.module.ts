import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppSessionComponent } from './pages/app-session/app-session.component';
import { authenticationGuardService, authorizationGuardService } from './shared/services/auth-guard-service/auth-guard-service.guard';


const routes: Routes = [
  {
    path: '', redirectTo: '/login', pathMatch: 'full'
  },
  {
    path: 'session', component: AppSessionComponent, canActivate: [authenticationGuardService],
    children: [
      {
        path: 'accueil',
        loadChildren: () => import('./pages/module-accueil/module-accueil.module')
          .then(module => module.ModuleAccueilModule)
      },
      {
        path: 'addresses-management', canActivate:[authorizationGuardService],
        loadChildren:
          () => import('./pages/module-address-manager/module-address-manager.module')
            .then(m => m.ModuleAddressManagerModule)
      },
      {
        path: 'companies-management', canActivate:[authorizationGuardService],
        loadChildren:
          () => import('./pages/module-company-manager/module-company-manager.module')
            .then(m => m.ModuleCompanyManagerModule)
      },
      {
        path: 'employees-management', canActivate:[authorizationGuardService],
        loadChildren:
          () => import('./pages/module-employee-manager/module-employee-manger.module')
            .then(m => m.ModuleEmployeeManagerModule)
      },
      {
        path: 'projects-management', canActivate:[authorizationGuardService],
        loadChildren:
          () => import('./pages/module-project-manager/module-project-manager.module')
            .then(m => m.ModuleProjectManagerModule)
      },
      {
        path: 'users-management',
        loadChildren: () => import('./pages/users-manager-module/users-manager.module')
          .then(m => m.UsersManagerModule)
      }
    ]
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }



