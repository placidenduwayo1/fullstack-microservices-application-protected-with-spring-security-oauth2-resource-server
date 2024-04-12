import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersManagerRoutingModule } from './users-manager-routing.module';
import { UserManagerComponent } from './user-manager/user-manager.component';
import { UsersPrinterComponent } from './user-manager/users-printer/users-printer.component';
import { CardModule } from 'primeng/card';
import { UserCreateComponent } from './user-manager/user-create/user-create.component';
import { UserUPrintComponent } from './user-manager/user-u-print/user-u-print.component';
import { MessagesModule } from 'primeng/messages';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TableModule } from 'primeng/table';
import { ConfirmationService, MessageService } from 'primeng/api';


@NgModule({
  declarations: [
    UserManagerComponent,
    UsersPrinterComponent,
    UserCreateComponent,
    UserUPrintComponent
  ],
  imports: [
    CommonModule,
    UsersManagerRoutingModule,
    CardModule,
    MessagesModule,
    ConfirmDialogModule,
    TableModule
  ],
  providers:[ConfirmationService, MessageService]
})
export class UsersManagerModule { }