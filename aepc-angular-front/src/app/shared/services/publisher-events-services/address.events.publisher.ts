import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { AddressEvent } from "../../models/events.model";

@Injectable({providedIn: "root"})
export class AddressEventPublisher {

  private addressEventSubject: Subject<AddressEvent> = new Subject<AddressEvent>();
  addressEventObservable: Observable<AddressEvent> = this.addressEventSubject.asObservable();
  publishAddressEvent(addressEvent: AddressEvent){
    this.addressEventSubject.next(addressEvent);
  }
}