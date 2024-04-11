import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from './menu/menu.component';
import {MenubarModule} from 'primeng/menubar';
import { MyMenuRoutingModule } from './menu-routing.module';



@NgModule({
  declarations: [
    MenuComponent
  ],
  imports: [
    CommonModule,
    MenubarModule,
    MyMenuRoutingModule
  ],
  exports: [MenuComponent]
})
export class MyMenuModule { }
