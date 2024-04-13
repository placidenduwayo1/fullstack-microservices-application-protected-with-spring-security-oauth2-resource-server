import { Company } from './../../../../shared/models/company/company.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit, inject } from '@angular/core';
import { CompanyEvent } from 'src/app/shared/models/events/events.model';
import { Type } from 'src/app/shared/models/company/company.type';
import { CompanyService } from 'src/app/shared/services/rest-services/companies.service';
import { CompanyEventServicePublisher } from 'src/app/shared/services/publisher-events-services/company.events.publisher';
import { Address } from 'src/app/shared/models/address/address.model';
import { AddressService } from 'src/app/shared/services/rest-services/addresses.service';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-sub-compo-company-update',
  templateUrl: './sub-compo-company-update.component.html',
  styleUrls: ['./sub-compo-company-update.component.scss'],
})
export class SubCompoCompanyUpdateComponent implements OnInit {
  
  companyForm!: FormGroup;
  private activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  private fbuilder: FormBuilder = inject(FormBuilder);
  private companyService: CompanyService = inject(CompanyService);
  private companyEventPublisher: CompanyEventServicePublisher = inject(CompanyEventServicePublisher);
  private addressService: AddressService = inject(AddressService);
  private confirmationService: ConfirmationService = inject(ConfirmationService);
  private messageService: MessageService = inject(MessageService);

  companyTypes = Type;
  addresses!: Address[]
  addressesMap: Map<string, string> = new Map();

  ngOnInit(): void {
    this.addressService.getAllAddresses().subscribe((data: Array<Address>) => {
      this.addresses = data;
      this.addresses.forEach((address: Address) => {
        this.addressesMap.set(
          address.addressId,
          address.num +
          ' ' +
          address.street +
          ', ' +
          address.pb +
          ' ' +
          address.city +
          ', ' +
          address.country
        );
      });
    });
    this.activatedRoute.data.subscribe(data => {
      const company: Company = data['getCompanyByIDResolve'];
      console.log(company);
      this.companyForm = this.fbuilder.group({
        companyId: [company.companyId],
        name: [
          company.name,
          [
            Validators.required,
            Validators.minLength(4),
            Validators.maxLength(20),
          ],
        ],
        type: [company.type, Validators.required],
        agency: [company.agency, [Validators.required, Validators.minLength(5)]],
        addressId: [company.addressId, Validators.required]
      });
    });

    this.companyEventPublisher.companyEventObservable.subscribe((event: CompanyEvent)=>{
      if(event==CompanyEvent.UPDATE_COMPANY){
        this.confirmationService.confirm({
          acceptLabel: 'Yes',
          rejectLabel: 'No',
          message: 'Do you realy want to update this company??',
          icon: 'pi pi-exclamation-triangle',
          accept: ()=>{
            this.companyService.updateCompany(this.companyForm.value).subscribe((updated: Company)=>{
              this.messageService.add({
                key: 'updated',
                severity: 'success',
                detail: 'succesfully updated',
                sticky: true
              });

              return updated;
            });
          },
          reject: ()=>{
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
    });
  }

  onCompanyUpdate() {
    this.companyEventPublisher.publishCompanyEvent(CompanyEvent.UPDATE_COMPANY);
  }
}
