import { SubcompoProjectUpdateComponent } from './compo-project-manager/subcompo-project-update/subcompo-project-update.component';
import { SubCompoProjectCreateComponent } from './compo-project-manager/sub-compo-project-create/sub-compo-project-create.component';
import { CompoProjectManagerComponent } from './compo-project-manager/compo-project-manager.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GetAllProjectsResolve, GetProjectByIDResolve } from 'src/app/shared/services/route-resolve-services/project-resolve/route.resolve';

const routes: Routes = [
  {
    path:'',
    component: CompoProjectManagerComponent,
    resolve:{
      getAllProjectsResolve: GetAllProjectsResolve
    }
  },
  {
    path:'project-create',
    component: SubCompoProjectCreateComponent,
  },
  {
    path:'project-update/:projectID',
    component: SubcompoProjectUpdateComponent,
    resolve:{
      getProjectByIDResolve:GetProjectByIDResolve
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ModuleProjectManagerRoutingModule { }
