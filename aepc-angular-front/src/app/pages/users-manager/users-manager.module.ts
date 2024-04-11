import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersManagerRoutingModule } from './users-manager-routing.module';
import { UserManagerComponent } from './user-manager/user-manager.component';
import { UsersPrinterComponent } from './user-manager/users-printer/users-printer.component';
import { CardModule } from 'primeng/card';
import { TableModule } from 'primeng/table';


@NgModule({
  declarations: [
    UserManagerComponent,
    UsersPrinterComponent
  ],
  imports: [
    CommonModule,
    UsersManagerRoutingModule,
    CardModule,
    TableModule
  ]
})
export class UsersManagerModule { }
