import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { UserEvent } from "../../models/events/events.model";
@Injectable({ providedIn: 'root' })
export class UserEventServicePublisher {
    private userEventPublisher: Subject<any> = new Subject();
    public userEventObservable : Observable<any> = this.userEventPublisher.asObservable();

    public publishUserEvent(event: UserEvent) {
        this.userEventPublisher.next(event);
    }
}