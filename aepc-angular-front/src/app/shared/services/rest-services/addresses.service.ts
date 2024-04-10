import { Address } from '../../models/address/address.model';
import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { myheaders } from './headers';

@Injectable({ providedIn: 'root' })
export class AddressService {

  private addressesServer = environment.gatewayService + '/api-address';
  private httpClient: HttpClient = inject(HttpClient);


  getAllAddresses(): Observable<Address[]> {
    return this.httpClient.get<Address[]>(this.addressesServer + '/addresses').pipe(
      map(addressesList => [...addressesList].sort((a, b) => b.pb - a.pb)),//afficher le tableau trié par ordre décroissant des cp
      map((sortedAddresses => sortedAddresses.filter(address => address.pb !== 10000)))
    )
  }

  createAddress(address: Address): Observable<Address> {
    return this.httpClient
      .post<Address>(this.addressesServer + '/addresses', address, { headers: myheaders });
  }

  getAddressByInfos(num: number, street: string, pb: number, city: string, country: string): Observable<Array<Address>> {
    return this.httpClient.get<Array<Address>>(
      this.addressesServer +
      '/addresses?num=' +
      num +
      '&street=' +
      street +
      '&pb=' +
      pb +
      '&city=' +
      city +
      '&country=' +
      country
    );
  }

  deleteAddress(addressId: string | any): Observable<void> {
    return this.httpClient.delete<void>(this.addressesServer + '/addresses/id/' + addressId);
  }

  getAddress(addressId: string | any): Observable<Address> {
    return this.httpClient.get<Address>(this.addressesServer + '/addresses/id/' + addressId);
  }

  updateAddress(address: Address): Observable<Address> {
    return this.httpClient.put<Address>(this.addressesServer + '/addresses/id/' + address.addressId, address, { headers: myheaders });
  }

  getEmployeesLiveAtAddress(addressId: string | any): Observable<Array<any>> {
    return this.httpClient.get<Array<any>>(this.addressesServer + '/employees/addresses/id/' + addressId);
  }
}
