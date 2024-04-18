
import { ActivatedRoute, Router } from '@angular/router';
import { AddressService } from '../../../../shared/services/rest-services/addresses.service';
import { Address } from '../../../../shared/models/address/address.model';
import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import { AddressEvent } from 'src/app/shared/models/events/events.model';
import { AddressEventServicePublisher } from 'src/app/shared/services/publisher-events-services/address.events.publisher';
import { ConfirmationService, MessageService } from 'primeng/api';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-sub-compo-address-printer',
  templateUrl: './sub-compo-address-printer.component.html',
  styleUrls: ['./sub-compo-address-printer.component.scss'],
})
export class SubCompoAddressPrinterComponent implements OnInit {
  @Input() addressesList!: Address[];
  @Output() nbrOfAddressesEmitter: EventEmitter<number> = new EventEmitter();

  private addressService: AddressService = inject(AddressService);
  private confirmationService: ConfirmationService = inject(ConfirmationService);
  private messageService: MessageService = inject(MessageService);

  private addressEventPublisher: AddressEventServicePublisher = inject(AddressEventServicePublisher);
  private router: Router = inject(Router);

  ngOnInit(): void {
    this.addressEventPublisher.addressEventObservable.subscribe((addressEvent: AddressEvent) => {
      switch (addressEvent) {
        case AddressEvent.EMPLOYEES_AT_ADDRESS:
          this.router.navigateByUrl(`session/addresses-management/employees-at-address/${this.addressEmployeesRelated}`);
          break;
        case AddressEvent.UPDATE_ADDRESS:
          this.router.navigateByUrl(`session/addresses-management/address-form-update/${this.addressId}`);
          break;
        case AddressEvent.DELETE_ADDRESS:
          this.confirmationService.confirm({
            acceptLabel: 'Yes',
            rejectLabel: 'No',
            message: 'Do you realy want to delete this address??',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
              this.addressService.deleteAddress(this.addressId).subscribe(() => {
                this.addressEventPublisher.publishAddressEvent(AddressEvent.REFRESH);
                this.messageService.add({
                  key: 'deleted',
                  severity: 'success',
                  detail: 'succesfully deleted',
                  sticky: true
                });
                return "address deleted";
              })
            },
            reject: () => {
              this.messageService.add({
                key: 'rejected',
                severity: 'error',
                detail: 'rejected',
                sticky: true
              });
              return null;
            }
          });
      }
    })
  }

  onPrintNbrAddressesEventEmitter(nbAddresses: number) {
    this.nbrOfAddressesEmitter.emit(nbAddresses);
  }

  addressId!: string
  onAddressUpdate(address: Address) {
    this.addressId = address.addressId;
    this.addressEventPublisher.publishAddressEvent(AddressEvent.UPDATE_ADDRESS);
  }

  onAddressDelete(addressId: string) {
    this.addressId = addressId;
    this.addressEventPublisher.publishAddressEvent(AddressEvent.DELETE_ADDRESS);
  }

addressEmployeesRelated!: string;
onPrintEmployeesLivigOnThisAddress(addressId: string){
  this.addressEmployeesRelated = addressId;
  this.addressEventPublisher.publishAddressEvent(AddressEvent.EMPLOYEES_AT_ADDRESS);
}
}
