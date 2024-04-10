import { ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ModuleProjectManagerRoutingModule } from './module-project-manager-routing.module';
import { CompoProjectManagerComponent } from './compo-project-manager/compo-project-manager.component';
import { SubCompoProjectPrinterComponent } from './compo-project-manager/sub-compo-project-printer/sub-compo-project-printer.component';
import { SubCompoProjectCreateComponent } from './compo-project-manager/sub-compo-project-create/sub-compo-project-create.component';
import { SubcompoProjectUpdateComponent } from './compo-project-manager/subcompo-project-update/subcompo-project-update.component';
import { CardModule } from 'primeng/card';
import { MessagesModule } from 'primeng/messages';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';


@NgModule({
  declarations: [
    CompoProjectManagerComponent,
    SubCompoProjectPrinterComponent,
    SubCompoProjectCreateComponent,
    SubcompoProjectUpdateComponent
  ],
  imports: [
    CommonModule,
    ModuleProjectManagerRoutingModule,
    TableModule,
    ReactiveFormsModule,
    CardModule,
    MessagesModule,
    ConfirmDialogModule
  ],
  providers: [MessageService, ConfirmationService]
})
export class ModuleProjectManagerModule { }
