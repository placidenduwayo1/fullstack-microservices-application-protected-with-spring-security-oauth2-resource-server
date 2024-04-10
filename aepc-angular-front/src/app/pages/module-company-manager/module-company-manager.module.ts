import { TableModule } from 'primeng/table';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ModuleCompanyManagerRoutingModule } from './module-company-manager-routing.module';
import { CompoCompanyManagerComponent } from './compo-company-manager/compo-company-manager.component';
import { SubCompoCompanyPrinterComponent } from './compo-company-manager/sub-compo-company-printer/sub-compo-company-printer.component';
import { SubCompoCompanyCreateComponent } from './compo-company-manager/sub-compo-company-create/sub-compo-company-create.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SubCompoCompanyUpdateComponent } from './compo-company-manager/sub-compo-company-update/sub-compo-company-update.component';
import { SubCompoPrintProjectsForCompanyComponent } from './compo-company-manager/sub-compo-print-projects-for-company/sub-compo-print-projects-for-company.component';
import { ButtonModule } from 'primeng/button';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CardModule } from 'primeng/card';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessagesModule } from 'primeng/messages';


@NgModule({
  declarations: [
    CompoCompanyManagerComponent,
    SubCompoCompanyPrinterComponent,
    SubCompoCompanyCreateComponent,
    SubCompoCompanyUpdateComponent,
    SubCompoPrintProjectsForCompanyComponent
  ],
  imports: [
    CommonModule,
    ModuleCompanyManagerRoutingModule,
    TableModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    CardModule,
    ConfirmDialogModule,
    MessagesModule
  ],
  providers: [MessageService, ConfirmationService]
})
export class ModuleCompanyManagerModule { }
