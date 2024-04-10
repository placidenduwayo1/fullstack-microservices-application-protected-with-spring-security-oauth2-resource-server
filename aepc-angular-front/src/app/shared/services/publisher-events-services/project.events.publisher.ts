import { Injectable } from "@angular/core";
import { Subject, Observable } from "rxjs";
import { ProjectEvent } from "../../models/events.model";

@Injectable({providedIn:'root'})
export class ProjectEventPublisher {
  private projectEventSubject : Subject<ProjectEvent> = new Subject<ProjectEvent>();
  projectEventObservable: Observable<ProjectEvent> = this.projectEventSubject.asObservable();
  publishProjectEvent(projectEvent: ProjectEvent){
    this.projectEventSubject.next(projectEvent);
  }
}