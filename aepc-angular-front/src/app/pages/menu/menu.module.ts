import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from './menu/menu.component';
import {MenubarModule} from 'primeng/menubar';
import { MyMenuRoutingModule } from './menu-routing.module';
import { UserPrinterComponent } from '../module-user-manager/user-manager/user-printer/user-printer.component';



@NgModule({
  declarations: [
    MenuComponent,
    UserPrinterComponent
  ],
  imports: [
    CommonModule,
    MenubarModule,
    MyMenuRoutingModule
  ],
  exports: [MenuComponent]
})
export class MyMenuModule { }
