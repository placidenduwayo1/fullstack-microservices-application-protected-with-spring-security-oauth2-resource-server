import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, ResolveFn } from "@angular/router";
import { Address } from "src/app/shared/models/address/address.model";
import { AddressService } from "../../rest-services/addresses.service";

export const GetAllAddressesResolve : ResolveFn<Array<Address>> = ()=>{
  return inject(AddressService).getAllAddresses();
}
export const GetAddressByIDResolve : ResolveFn<Address> = (route: ActivatedRouteSnapshot)=>{
  return inject(AddressService).getAddress(route.paramMap.get('addressId'));
}

export const GetEmployeesAtAddressResolve: ResolveFn<Array<any>> = (route: ActivatedRouteSnapshot)=>{
  return inject(AddressService).getEmployeesLiveAtAddress(route.paramMap.get('addressId'));
}