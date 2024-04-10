import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { NgOptimizedImage } from '@angular/common'

import { ModuleAccueilRoutingModule } from './module-accueil-routing.module';
import { CompoAccueilComponent } from './compo-accueil/compo-accueil.component';


@NgModule({
  declarations: [
    CompoAccueilComponent
  ],
  imports: [
    CommonModule,
    ModuleAccueilRoutingModule,
    CardModule,
    NgOptimizedImage
  ]
})
export class ModuleAccueilModule { }
