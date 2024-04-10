import { GetProjectsAssignedToEmployeeResolve } from '../../shared/services/route-resolve-services/employee-resolve/employee.route.resolve';
import { SubCompoPrintProjectsAssignedtoEmployeeComponent } from './compo-employee-manager/sub-compo-print-projects-assignedto-employee/sub-compo-print-projects-assignedto-employee.component';
import { SubCompoEmployeeUpdateComponent } from './compo-employee-manager/sub-compo-employee-update/sub-compo-employee-update.component';
import { SubCompoEmployeeCreateComponent } from './compo-employee-manager/sub-compo-employee-create/sub-compo-employee-create.component';
import { CompoEmployeeManagerComponent } from './compo-employee-manager/compo-employee-manager.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GetAllEmployeesResolve, GetEmployeeByIDResolve } from 'src/app/shared/services/route-resolve-services/employee-resolve/employee.route.resolve';

const routes: Routes = [
  {
    path: '',
    component: CompoEmployeeManagerComponent,
    resolve: {
      getAllEmployeesResolve: GetAllEmployeesResolve,
    }
  },
  {
    path: 'employee-form-create',
    component: SubCompoEmployeeCreateComponent,
  },
  {
    path: 'employee-form-update/:employeeId',
    component: SubCompoEmployeeUpdateComponent,
    resolve:{
      getEmployeeByIDResolve:GetEmployeeByIDResolve
    }
  },
  {
    path:'projects-assignedto-employees/:employeeId',
    component: SubCompoPrintProjectsAssignedtoEmployeeComponent,
    resolve:{
      getProjectsAssignedToEmployeeResolve: GetProjectsAssignedToEmployeeResolve
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModuleEmployeeManagerRoutingModule {}
