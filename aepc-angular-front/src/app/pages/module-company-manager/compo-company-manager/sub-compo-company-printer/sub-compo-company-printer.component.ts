import { Router } from '@angular/router';
import { Company } from '../../../../shared/models/company/company.model';
import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import { CompanyEvent } from 'src/app/shared/models/events.model';
import { CompanyService } from 'src/app/shared/services/rest-services/companies.service';
import { CompanyEventPublisher } from 'src/app/shared/services/publisher-events-services/company.events.publisher';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-sub-compo-company-printer',
  templateUrl: './sub-compo-company-printer.component.html',
  styleUrls: ['./sub-compo-company-printer.component.scss']
})
export class SubCompoCompanyPrinterComponent implements OnInit {

  private companyEventPublisher: CompanyEventPublisher = inject(CompanyEventPublisher);
  private companyService: CompanyService = inject(CompanyService);
  private router: Router = inject(Router);
  private confirmationService: ConfirmationService = inject(ConfirmationService);
  private messageService: MessageService = inject(MessageService);

  @Input() companies!: Array<Company>;
  @Output() nbrOfCompaniesEventEmitter: EventEmitter<number> = new EventEmitter();

  ngOnInit(): void {
    this.companyEventPublisher.companyEventObservable.subscribe((companyEvent: CompanyEvent) => {
      switch (companyEvent) {
        case CompanyEvent.UPDATE_COMPANY_FORM:
          this.router.navigateByUrl('session/companies-management/company-update/' + this.idToUpdate);
          console.log(companyEvent);
          break;
        case CompanyEvent.PROJECTS_ASSIGNEDTO_COMPANY:
          this.router.navigateByUrl('session/companies-management/projects-assignedto-company/' + this.companyIdProjectsRelated);
          console.log(companyEvent);
          break;
        case CompanyEvent.COMPANY_DELETED:
          this.confirmationService.confirm({
            acceptLabel: 'Yes',
            rejectLabel: 'No',
            message: 'Do you realy want to delete this company??',
            icon: 'pi pi-exclamation-triangle',
            accept: ()=>{
              this.companyService.deleteCompany(this.idCompanyToDelete).subscribe(()=>{
                this.companyEventPublisher.publishCompanyEvent(CompanyEvent.REFRESH);
                this.messageService.add({
                  key: 'deleted',
                  severity: 'success',
                  detail: 'succesfully deleted',
                  sticky: true
                });
                return "deleted";
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
          break;
      }
    })
  }

  onPrintNumberOfCompaniesEventEmitter(nb: number) {
    this.nbrOfCompaniesEventEmitter.emit(nb);
  }

  idToUpdate!: string;
  onCompanyUpdate(company: Company) {
    this.idToUpdate = company.companyId;
    this.companyEventPublisher.publishCompanyEvent(CompanyEvent.UPDATE_COMPANY_FORM)
  }
  private idCompanyToDelete!:string;
  onCompanyDelete(companyId: string) {
    this.idCompanyToDelete = companyId;
    this.companyEventPublisher.publishCompanyEvent(CompanyEvent.COMPANY_DELETED);
  }

  companyIdProjectsRelated!: string;
  onPrintProjectsRelated(companyId: string) {
    this.companyIdProjectsRelated = companyId;
    this.companyEventPublisher.publishCompanyEvent(CompanyEvent.PROJECTS_ASSIGNEDTO_COMPANY)
  }
}
