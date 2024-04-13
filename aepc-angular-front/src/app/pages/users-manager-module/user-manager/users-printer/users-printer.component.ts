import { Component, Input, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { UserEvent } from 'src/app/shared/models/events/events.model';
import { AppUser } from 'src/app/shared/models/user-auth/user.model';
import { UsersManagementService } from 'src/app/shared/services/app-user-service/users.service';
import { UserEventServicePublisher } from 'src/app/shared/services/publisher-events-services/user.events.pyblisher';

@Component({
  selector: 'app-users-printer',
  templateUrl: './users-printer.component.html',
  styleUrls: ['./users-printer.component.scss']
})
export class UsersPrinterComponent implements OnInit {

  @Input() users!: Array<any>;
  private userEventPublisher = inject(UserEventServicePublisher);
  private userService = inject(UsersManagementService);
  private router = inject(Router);
  private msgService = inject(MessageService);
  private confirmService = inject(ConfirmationService);

  ngOnInit(): void {
    this.userEventPublisher.userEventObservable.subscribe((event: UserEvent) => {
      switch (event) {
        case UserEvent.ADD_ROLE_TO_USER:
          console.log(event);
          this.router.navigateByUrl(`session/users-management/add-role-user/${this.idUserToUpdate}`);
          break;
        case UserEvent.DELETE_USER:
          console.log(event);
          this.confirmService.confirm({
            acceptLabel: 'Yes',
            rejectLabel: 'No',
            message: 'Do you really want to delete this user??',

            accept: () => {
              this.userService.delete(this.idUserToDelete).subscribe(
                ()=>{
                  this.userEventPublisher.publishUserEvent(UserEvent.REFRESH)
                  this.msgService.add({
                    key:'delete',
                    severity: 'success',
                    detail: 'user deleted successfully',
                    sticky: true
                  });
                }
              );
            },
            reject: ()=>{
              this.msgService.add({
                key:'reject',
                severity: 'error',
                detail: 'user deletion rejected',
                sticky: true
              });
            }
          });
          break;
      }
    });
  }
  idUserToUpdate!: number;
  onUpdate(user: AppUser) {
    this.idUserToUpdate = user.userId;
    this.userEventPublisher.publishUserEvent(UserEvent.ADD_ROLE_TO_USER);
  }
  idUserToDelete!: number;
  onDelete(userId: number) {
    this.idUserToDelete = userId;
    this.userEventPublisher.publishUserEvent(UserEvent.DELETE_USER);
  }
}
