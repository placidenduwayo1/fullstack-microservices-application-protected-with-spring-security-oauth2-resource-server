import { SubCompoPrintEmployeesAtAddressComponent } from './compo-address-manager/sub-compo-print-employees-at-address/sub-compo-print-employees-at-address.component';
import { GetAddressByIDResolve, GetEmployeesAtAddressResolve } from '../../shared/services/route-resolve-services/address-resolve/addresse.route.resolve';
import { CompoAddressPrinterComponent as CompoAddressManagerComponent } from './compo-address-manager/compo-address-manager.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SubCompoAddressCreateComponent } from './compo-address-manager/sub-compo-address-create/sub-compo-address-create.component';
import { SubCompoAddressUpdateComponent } from './compo-address-manager/sub-compo-address-update/sub-compo-address-update.component';
import { GetAllAddressesResolve } from 'src/app/shared/services/route-resolve-services/address-resolve/addresse.route.resolve';

const routes: Routes = [
  {
    path :"", component: CompoAddressManagerComponent,
    resolve:{
      getAllAddressesResolve: GetAllAddressesResolve
    }
  }
  ,
  {
    path:"address-form-create",
    component: SubCompoAddressCreateComponent
  },
  {
    path:'address-form-update/:addressId',
    component: SubCompoAddressUpdateComponent,
    resolve:{
      getAddressByIDResolve: GetAddressByIDResolve
    }
  },
  {
    path:'employees-at-address/:addressId',
    component: SubCompoPrintEmployeesAtAddressComponent,
    resolve:{
      getEmployeesAtAddressResolve: GetEmployeesAtAddressResolve
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ModuleAddressManagerRoutingModule { }

