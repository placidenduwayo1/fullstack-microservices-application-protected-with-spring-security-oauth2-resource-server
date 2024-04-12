import { Company } from '../../../shared/models/company/company.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit, inject } from '@angular/core';
import { CompanyEvent } from 'src/app/shared/models/events.model';
import { CompanyService } from 'src/app/shared/services/rest-services/companies.service';
import { CompanyEventPublisher } from 'src/app/shared/services/publisher-events-services/company.events.publisher';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-compo-company-manager',
  templateUrl: './compo-company-manager.component.html',
  styleUrls: ['./compo-company-manager.component.scss'],
})
export class CompoCompanyManagerComponent implements OnInit {

  private activatedRoute = inject(ActivatedRoute);
  private companyEventPublisher = inject(CompanyEventPublisher);
  private companyService = inject(CompanyService);

  companies!: Array<Company>;
  nbCompanies!: number;
  public getCompaniesNumber($event: any) {
    this.nbCompanies = $event;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.companies = data['getAllCompaniesResolve']
    });
    this.companyEventPublisher.companyEventObservable.subscribe((event: CompanyEvent) => {
      if (event == CompanyEvent.REFRESH) {
        this.companyService.getAllCompanies().subscribe((data: Array<Company>) => {
          this.companies = data;
        });
      }
    });
  }
}
