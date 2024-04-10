import { Injectable } from "@angular/core";
import { Subject, Observable } from "rxjs";
import { EmployeeEvent } from "../../models/events.model";

@Injectable({providedIn:"root"})
export class EmployeeEventPublisher {

  private employeeEventSubject: Subject<EmployeeEvent> = new Subject<EmployeeEvent>(); //create an event publisher
  employeeEnventObservable: Observable<EmployeeEvent> = this.employeeEventSubject.asObservable(); //create an event observable

  publishEmployeeEvent(employeeEvent: EmployeeEvent){
    this.employeeEventSubject.next(employeeEvent);
  }
}