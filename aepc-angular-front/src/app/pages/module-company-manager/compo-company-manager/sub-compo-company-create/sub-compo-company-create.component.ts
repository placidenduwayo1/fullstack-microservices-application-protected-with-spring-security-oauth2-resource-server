import { Company } from '../../../../shared/models/company/company.model';
import { Component, OnInit, inject } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Address } from 'src/app/shared/models/address/address.model';
import { Type } from 'src/app/shared/models/company/company.type';
import { CompanyEvent } from 'src/app/shared/models/events.model';
import { CompanyEventPublisher } from 'src/app/shared/services/publisher-events-services/company.events.publisher';
import { AddressService } from 'src/app/shared/services/rest-services/addresses.service';
import { CompanyService } from 'src/app/shared/services/rest-services/companies.service';

@Component({
  selector: 'app-sub-compo-company-create',
  templateUrl: './sub-compo-company-create.component.html',
  styleUrls: ['./sub-compo-company-create.component.scss']
})
export class SubCompoCompanyCreateComponent  implements OnInit{

  types = Type;

  addresses!: Array<Address>;
  addressesMap: Map<string, string> = new Map();

  private addressService: AddressService = inject(AddressService);
  private companyService: CompanyService = inject(CompanyService);
  private companyEventPublisher:CompanyEventPublisher = inject(CompanyEventPublisher);
  private confirmationService: ConfirmationService = inject(ConfirmationService);
  private messageService: MessageService = inject(MessageService);
  
  company: Company = new Company();

  ngOnInit(): void {
    
    this.addressService.getAllAddresses().subscribe(data=>{
      this.addresses = data;
      this.addresses.forEach(address=>{
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
      })
    });

    this.companyEventPublisher.companyEventObservable.subscribe((event: CompanyEvent)=>{
      switch(event) {
        case CompanyEvent.CREATE_COMPANY:
          this.confirmationService.confirm({
            acceptLabel: 'Yes',
            rejectLabel: 'No',
            message: 'Do you realy want to create this company??',
            icon: 'pi pi-exclamation-triangle',
            accept: ()=>{
              this.companyService.createCompany(this.company).subscribe((newCompany: Company)=>{
                this.messageService.add({
                  key: 'added',
                  severity: 'success',
                  detail: 'succesfully added',
                  sticky: true
                });

                return newCompany;
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
    })
  }

  onCompanyCreate(){
    this.companyEventPublisher.publishCompanyEvent(CompanyEvent.CREATE_COMPANY);
  }
}
