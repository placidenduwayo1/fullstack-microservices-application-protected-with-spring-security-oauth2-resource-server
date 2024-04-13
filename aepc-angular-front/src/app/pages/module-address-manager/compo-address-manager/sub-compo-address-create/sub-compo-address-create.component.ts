import { ConfirmationService, MessageService } from 'primeng/api';
import { Address } from '../../../../shared/models/address/address.model';
import { AddressService } from '../../../../shared/services/rest-services/addresses.service';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AddressEvent } from 'src/app/shared/models/events/events.model';
import { AddressEventServicePublisher } from 'src/app/shared/services/publisher-events-services/address.events.publisher';

@Component({
  selector: 'app-sub-compo-address-create',
  templateUrl: './sub-compo-address-create.component.html',
  styleUrls: ['./sub-compo-address-create.component.scss']
})
export class SubCompoAddressCreateComponent implements OnInit {

  addressForm!: FormGroup;
  private formBuilder: FormBuilder = inject(FormBuilder);
  private addressService: AddressService = inject(AddressService);
  private confirmationService: ConfirmationService = inject(ConfirmationService);
  private messageService: MessageService = inject(MessageService);
  private addressEventPublisher: AddressEventServicePublisher = inject(AddressEventServicePublisher);

  ngOnInit(): void {
    this.addressForm = this.formBuilder.group({
      addressId: ['', Validators.required],
      num: ['', [Validators.required, Validators.min(1)]],
      street: ['', [Validators.required, Validators.minLength(5)]],
      pb: ['', [Validators.required, Validators.min(10000)]],
      city: ['', [Validators.required, Validators.minLength(3)]],
      country: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]]
    });

    this.addressEventPublisher.addressEventObservable.subscribe((event: AddressEvent)=>{
      switch(event) {
        case AddressEvent.SAVE_ADDRESS_FORM_DATA:
          this.confirmationService.confirm({
            acceptLabel: 'Yes',
            rejectLabel: 'No',
            message: 'Do you realy want to add this address??',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
              this.addressService.createAddress(this.addressForm.value).subscribe((newAddress: Address) => {
                console.log(newAddress);
                this.messageService.add({
                  key: 'added',
                  severity: 'success',
                  detail: 'succesfully added',
                  sticky: true
                });
                return newAddress;
              })
            },
            reject: () => {
              this.messageService.add({
                key: 'rejected',
                severity: 'error',
                detail: 'succesfully added',
                sticky: true
              });
              return null;
            }
          });
      }
    })
  }

  onAddressFormSubmit() {
    this.addressEventPublisher.publishAddressEvent(AddressEvent.SAVE_ADDRESS_FORM_DATA);
  }

}
