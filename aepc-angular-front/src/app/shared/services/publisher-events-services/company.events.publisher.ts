import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { CompanyEvent } from "../../models/events.model";

@Injectable({providedIn:'root'})
export class CompanyEventPublisher {
  private companyEventSubject: Subject<CompanyEvent> = new Subject<CompanyEvent>();
  companyEventObservable: Observable<CompanyEvent> = this.companyEventSubject.asObservable();
  publishCompanyEvent(companyEvent: CompanyEvent){
    this.companyEventSubject.next(companyEvent);
  }
}
