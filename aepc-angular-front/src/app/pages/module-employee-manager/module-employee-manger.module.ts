import { TableModule } from 'primeng/table';
import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ModuleEmployeeManagerRoutingModule } from './module-employee-manager-routing.module';
import { CompoEmployeeManagerComponent } from './compo-employee-manager/compo-employee-manager.component';
import { SubCompoEmployeePrinterComponent } from './compo-employee-manager/sub-compo-employee-printer/sub-compo-employee-printer.component';
import { SubCompoEmployeeCreateComponent } from './compo-employee-manager/sub-compo-employee-create/sub-compo-employee-create.component';
import { SubCompoEmployeeUpdateComponent } from './compo-employee-manager/sub-compo-employee-update/sub-compo-employee-update.component';
import { SubCompoPrintProjectsAssignedtoEmployeeComponent } from './compo-employee-manager/sub-compo-print-projects-assignedto-employee/sub-compo-print-projects-assignedto-employee.component';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessagesModule } from 'primeng/messages';
import { ConfirmationService, MessageService } from 'primeng/api';


@NgModule({
  declarations: [
    CompoEmployeeManagerComponent,
    SubCompoEmployeePrinterComponent,
    SubCompoEmployeeCreateComponent,
    SubCompoEmployeeUpdateComponent,
    SubCompoPrintProjectsAssignedtoEmployeeComponent
  ],
  imports: [
    CommonModule,
    ModuleEmployeeManagerRoutingModule,
    ReactiveFormsModule,
    TableModule,
    ButtonModule,
    CardModule,
    ConfirmDialogModule,
    MessagesModule
  ],
  providers:[MessageService, ConfirmationService]

})
export class ModuleEmployeeManagerModule { }
