import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Observable } from 'rxjs';
import { UserEvent } from 'src/app/shared/models/events/events.model';
import { AppUser } from 'src/app/shared/models/user-auth/user.model';
import { UsersManagementService } from 'src/app/shared/services/app-user-service/users.service';
import { UserEventServicePublisher } from 'src/app/shared/services/publisher-events-services/user.events.pyblisher';

@Component({
  selector: 'app-user-role-manager',
  templateUrl: './user-role-manager.component.html',
  styleUrls: ['./user-role-manager.component.scss']
})
export class UserRoleManagerComponent implements OnInit {

  private activatedRoute = inject(ActivatedRoute);
  userId!: number;
  private userService = inject(UsersManagementService);
  private fb = inject(FormBuilder);
  private userEventPublisher = inject(UserEventServicePublisher);
  private confirmService = inject(ConfirmationService);
  private msgService = inject(MessageService);
  roleUserForm!: FormGroup;
  user!: AppUser

  operations: any[] = [
    { name: 'add-role-user', key: 'add-role-user' },
    { name: 'remove-role-user', key: 'remove-role-user' },
    { name: 'change-password', key: 'change-password' }];

  selectedOp: any = null;

  ngOnInit(): void {
    this.userId = this.activatedRoute.snapshot.params['userId'];

    this.selectedOp = this.operations[0];

    this.userEventPublisher.userEventObservable.subscribe((event: UserEvent) => {
      switch (event) {
        case UserEvent.ADD_ROLE_USER:
          console.log(event);
          this.userService.getUser(this.userId).subscribe({
            next: (data) => {
              this.user = data;
              this.roleUserForm = this.fb.group({
                username: [this.user.username],
                role: ['', Validators.required]
              });
            }
          });
          this.userEventPublisher.userEventObservable.subscribe(newEvent=>{
            if(newEvent==UserEvent.MANAGE_USER_EVENT){
              this.confirmService.confirm({
                acceptLabel: 'Yes',
                rejectLabel:'No',
                message: 'Confirm new Role to user',
                accept: ()=>{
                  this.userService.addRoleUser(this.roleUserForm.value).subscribe(newUser=>{
                    console.log("new User ", newUser);
                    this.msgService.add({
                      key: 'add',
                      severity: 'success',
                      detail: 'Successfully updated',
                      sticky: true
                    });
                  });
                },
                reject: ()=>{
                  this.msgService.add({
                    key: 'reject',
                    severity: 'error',
                    detail: 'rejected',
                    sticky: true
                  });
                }
               });
            }
          });
          break;

        case UserEvent.REMOVE_ROLE_USER:
          console.log(event);
          this.userService.getUser(this.userId).subscribe({
            next: (data) => {
              this.user = data;
              this.roleUserForm = this.fb.group({
                username: [this.user.username],
                role: ['', Validators.required]
              });
            }
          });
          this.userEventPublisher.userEventObservable.subscribe(newEvent=>{
            if(newEvent==UserEvent.MANAGE_USER_EVENT){
              this.confirmService.confirm({
                acceptLabel: 'Yes',
                rejectLabel:'No',
                message: 'Confirm remove role to user',
                accept: ()=>{
                  this.userService.removeRoleFromUser(this.roleUserForm.value).subscribe(newUser=>{
                    console.log("new User ", newUser);
                    this.msgService.add({
                      key: 'remove',
                      severity: 'success',
                      detail: 'Successfully updated',
                      sticky: true
                    });
                  });
                },
                reject: ()=>{
                  this.msgService.add({
                    key: 'reject',
                    severity: 'error',
                    detail: 'rejected',
                    sticky: true
                  });
                }
               });
            }
          });
          break;

        case UserEvent.OPEN_PWD_CHANGE_UI:
          console.log(event);
          break;
        default:
          console.log(event);
      }
    })
  }

  addRoleToUser() {
    this.userEventPublisher.publishUserEvent(UserEvent.ADD_ROLE_USER);
  }
  removeRoleFromUser() {
    this.userEventPublisher.publishUserEvent(UserEvent.REMOVE_ROLE_USER);
  }
  openPwdChangeUI() {
    this.userEventPublisher.publishUserEvent(UserEvent.OPEN_PWD_CHANGE_UI);
  }

  onSubmit() {
    this.userEventPublisher.publishUserEvent(UserEvent.MANAGE_USER_EVENT);
  }
}
