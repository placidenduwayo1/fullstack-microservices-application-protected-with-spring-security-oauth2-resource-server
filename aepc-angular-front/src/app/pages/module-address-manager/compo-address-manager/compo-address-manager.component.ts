import { AddressService } from '../../../shared/services/rest-services/addresses.service';
import { Address } from '../../../shared/models/address/address.model';
import { AddressEvent } from '../../../shared/models/events.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AddressEventPublisher } from 'src/app/shared/services/publisher-events-services/address.events.publisher';

@Component({
  selector: 'app-compo-address-manager',
  templateUrl: './compo-address-manager.component.html',
  styleUrls: ['./compo-address-manager.component.scss']
})
export class CompoAddressPrinterComponent implements OnInit {

  constructor(
    private activatedRoute: ActivatedRoute,
    private addressEventPubliser : AddressEventPublisher,
    private addressService: AddressService,
    private router: Router) { }

  addressesList!: Array<Address>;
  nbAddresses!:number;

  onPrintNbrOfAddresses($event: number){
    this.nbAddresses = $event;
  }

  ngOnInit(): void {
    this.addressEventPubliser.addressEventObservable.subscribe((event: AddressEvent)=>{
      switch(event){

        case AddressEvent.GET_ALL_ADDRESSES:
          this.activatedRoute.data.subscribe(addresses=>{
            this.addressesList = addresses["getAllAddressesResolve"];
            console.log(event)
          });
          console.log(event);
          break;

        case AddressEvent.CREATE_ADDRESS_FORM:
          console.log(event);
          this.router.navigateByUrl("session/addresses-management/address-form-create");
          break;

        case AddressEvent.REFRESH:
          this.addressService.getAllAddresses().subscribe((addresses: Array<Address>)=>{
            this.addressesList=addresses
          });
          break;
      }
    });
  }

  onPrintAddresses(){
    this.addressEventPubliser.publishAddressEvent(AddressEvent.GET_ALL_ADDRESSES)
  }

  onCreateAddress(){
    this.addressEventPubliser.publishAddressEvent(AddressEvent.CREATE_ADDRESS_FORM);
  }
}
