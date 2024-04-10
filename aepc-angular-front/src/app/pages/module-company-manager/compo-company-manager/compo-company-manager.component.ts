import { Company } from '../../../shared/models/company/company.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { CompanyEvent } from 'src/app/shared/models/events.model';
import { CompanyService } from 'src/app/shared/services/rest-services/companies.service';
import { CompanyEventPublisher } from 'src/app/shared/services/publisher-events-services/company.events.publisher';

@Component({
  selector: 'app-compo-company-manager',
  templateUrl: './compo-company-manager.component.html',
  styleUrls: ['./compo-company-manager.component.scss'],
})
export class CompoCompanyManagerComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private companyEventPublisher: CompanyEventPublisher,
    private companyService: CompanyService
  ) {}

  companies!: Array<Company>;
  nbCompanies!: number;
  public getCompaniesNumber($event: any){
    this.nbCompanies = $event;
  }

  ngOnInit(): void {
    this.companyEventPublisher.companyEventObservable.subscribe(
      (companyEvent: CompanyEvent) => {
        switch (companyEvent) {
          case CompanyEvent.GET_ALL_COMPANIES:
            this.activatedRoute.data.subscribe((data) => {
              console.log(companyEvent);
              this.companies = data['getAllCompaniesResolve'];
              console.log(this.companies);
            });
            break;

          case CompanyEvent.COMPANY_DELETED:
            this.companyService.getAllCompanies().subscribe((data: Array<Company>)=>{
              this.companies=data;
            })
            break;
        }
      }
    );
  }
  onPrintCompanies() {
    this.companyEventPublisher.publishCompanyEvent(
      CompanyEvent.GET_ALL_COMPANIES
    );
  }
}
