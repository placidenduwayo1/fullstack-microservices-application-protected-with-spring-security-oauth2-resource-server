import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ModuleAddressManagerRoutingModule } from './module-address-manager-routing.module';
import { CompoAddressPrinterComponent as CompoAddressManagerComponent } from './compo-address-manager/compo-address-manager.component';
import { SubCompoAddressPrinterComponent } from './compo-address-manager/sub-compo-address-printer/sub-compo-address-printer.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SubCompoAddressCreateComponent } from './compo-address-manager/sub-compo-address-create/sub-compo-address-create.component';
import { SubCompoAddressUpdateComponent } from './compo-address-manager/sub-compo-address-update/sub-compo-address-update.component';
import { SubCompoPrintEmployeesAtAddressComponent } from './compo-address-manager/sub-compo-print-employees-at-address/sub-compo-print-employees-at-address.component';
import { MessagesModule,  } from 'primeng/messages';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { CardModule } from 'primeng/card';

@NgModule({
  declarations: [
    CompoAddressManagerComponent,
    SubCompoAddressPrinterComponent,
    SubCompoAddressCreateComponent,
    SubCompoAddressUpdateComponent,
    SubCompoPrintEmployeesAtAddressComponent
  ],
  imports: [
    CommonModule,
    ModuleAddressManagerRoutingModule,
    ReactiveFormsModule,
    TableModule,
    MessagesModule,
    ConfirmDialogModule,
    CardModule
  ],
  providers: [ConfirmationService, MessageService]
})
export class ModuleAddressManagerModule { }
