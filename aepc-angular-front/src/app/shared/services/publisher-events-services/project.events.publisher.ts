import { Injectable } from "@angular/core";
import { Subject, Observable } from "rxjs";
import { ProjectEvent } from "../../models/events/events.model";

@Injectable({providedIn:'root'})
export class ProjectEventServicePublisher {
  private projectEventSubject : Subject<ProjectEvent> = new Subject<ProjectEvent>();
  public projectEventObservable: Observable<ProjectEvent> = this.projectEventSubject.asObservable();
  publishProjectEvent(projectEvent: ProjectEvent){
    this.projectEventSubject.next(projectEvent);
  }
}