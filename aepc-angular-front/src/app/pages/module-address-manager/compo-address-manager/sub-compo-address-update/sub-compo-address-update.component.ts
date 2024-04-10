import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AddressService } from '../../../../shared/services/rest-services/addresses.service';
import { Component, OnInit, inject } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Address } from 'src/app/shared/models/address/address.model';
import { AddressEventPublisher } from 'src/app/shared/services/publisher-events-services/address.events.publisher';
import { AddressEvent } from 'src/app/shared/models/events.model';

@Component({
  selector: 'app-sub-compo-address-update',
  templateUrl: './sub-compo-address-update.component.html',
  styleUrls: ['./sub-compo-address-update.component.scss']
})
export class SubCompoAddressUpdateComponent implements OnInit {
  addressForm!: FormGroup;

  private formBuilder: FormBuilder = inject(FormBuilder);
  private addressService: AddressService = inject(AddressService);
  private confirmationService: ConfirmationService = inject(ConfirmationService);
  private messageService: MessageService = inject(MessageService);
  private activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  private addressEventPublisher: AddressEventPublisher = inject(AddressEventPublisher);

  ngOnInit(): void {

    this.activatedRoute.data.subscribe((data) => {
      let address = data['getAddressByIDResolve'];
      this.addressForm = this.formBuilder.group({
        addressId: [address.addressId],
        num: [address.num, [Validators.required, Validators.min(1)]],
        street: [address.street, [Validators.required, Validators.minLength(5)]],
        pb: [address.pb, [Validators.required, Validators.min(10000)]],
        city: [address.city, [Validators.required, Validators.minLength(3)]],
        country: [address.country, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]]
      });
    });

    this.addressEventPublisher.addressEventObservable.subscribe((event: AddressEvent) => {
      switch (event) {
        case AddressEvent.UPDATE_ADDRESS:
          this.confirmationService.confirm({
            acceptLabel: 'Yes',
            rejectLabel: 'No',
            message: 'Do you realy want to update this address??',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
              this.addressService.updateAddress(this.addressForm.value).subscribe(
                (updatedAddress: Address) => {
                  this.messageService.add({
                    key: 'updated',
                    severity: 'success',
                    detail: 'succesfully updated',
                    sticky: true
                  });

                  return updatedAddress;
                });
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

  onAddressFormSubmit() {
    this.addressEventPublisher.publishAddressEvent(AddressEvent.UPDATE_ADDRESS);
  }

}
