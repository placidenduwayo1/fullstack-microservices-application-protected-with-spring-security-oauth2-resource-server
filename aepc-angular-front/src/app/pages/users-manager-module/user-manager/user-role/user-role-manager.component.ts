import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Observable } from 'rxjs';
import { UserEvent } from 'src/app/shared/models/events/events.model';
import { AppRole } from 'src/app/shared/models/user-auth/role.model';
import { AppUser } from 'src/app/shared/models/user-auth/user.model';
import { UsersManagementService } from 'src/app/shared/services/app-user-service/users.service';
import { UserEventServicePublisher } from 'src/app/shared/services/publisher-events-services/user.events.publisher';

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
  chgPwdForm!: FormGroup;

  user$!: Observable<AppUser>;
  user!: AppUser;

  operations: any[] = [
    { name: 'add-role-user', key: 'add-role-user' },
    { name: 'remove-role-user', key: 'remove-role-user' },
    { name: 'change-password', key: 'change-password' }];

  selectedOp: any = null;

  ngOnInit(): void {
    this.userId = this.activatedRoute.snapshot.params['userId'];

    this.selectedOp = this.operations[0];
    let userEvent$: Observable<UserEvent> = this.userEventPublisher.userEventObservable;
    userEvent$.subscribe({
      next: (event1: UserEvent) => {
        switch (event1) {
          case UserEvent.ADD_ROLE_USER:
            console.log(event1);
            this.user$ = this.userService.getUserById(this.userId);
            this.user$.subscribe({
              next: (appUser: AppUser) => {
                this.user = appUser;
                this.roleUserForm = this.fb.group({
                  username: [{ value: appUser.username, disabled: true }],
                  role: ['', [Validators.required, Validators.minLength(3)]]
                });
              },
              error: (err) => {
                console.log(`an error occured ${err}`);
              },
              complete: () => {
                console.log("observale complete emission");
                this.userEventPublisher.userEventObservable.subscribe((event2: UserEvent) => {
                  if (event2 == UserEvent.MANAGE_USER) {
                    this.confirmService.confirm({
                      acceptLabel: 'Yes',
                      rejectLabel: 'No',
                      message: 'Confirm new Role to user',
                      accept: () => {
                        const username = this.roleUserForm.getRawValue().username;
                        const role = this.roleUserForm.value.role;
                        const roles = this.user.roles;
                        console.log(roles);
                        const appRole$ = this.userService.getRoleByName(role);
                        appRole$.subscribe((appRole: AppRole) => {
                          console.log(appRole);
                          if (roles.filter(value => value == appRole).length > 0) {
                            console.log("user has already this role")
                          }
                          else {
                            this.userService.addRoleUser(username, role).subscribe(newUser => {
                              console.log("new User ", newUser);
                              this.msgService.add({
                                key: 'add',
                                severity: 'success',
                                detail: 'Role successfully added to user ' + this.user.username,
                                sticky: true
                              });
                            });
                          }
                        });

                      },
                      reject: () => {
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
              }
            });
            break;

          case UserEvent.REMOVE_ROLE_USER:
            console.log(event1);
            this.user$ = this.userService.getUserById(this.userId);
            this.user$.subscribe({
              next: (value: AppUser) => {
                this.user = value;
                this.roleUserForm = this.fb.group({
                  username: [{ value: this.user.username, disabled: true }],
                  role: ['', [Validators.required, Validators.minLength(3)]]
                });
              },
              error: (err) => {
                console.log(`error occured ${err}`)
              },
              complete: () => {
                console.log("observale complete emission");
                this.userEventPublisher.userEventObservable.subscribe((event3: UserEvent) => {
                  if (event3 == UserEvent.MANAGE_USER) {
                    this.confirmService.confirm({
                      acceptLabel: 'Yes',
                      rejectLabel: 'No',
                      message: 'Confirm remove Role from user',
                      accept: () => {
                        const username = this.roleUserForm.getRawValue().username;
                        const role = this.roleUserForm.value.role;
                        this.userService.removeRoleFromUser(username, role).subscribe(newUser => {
                          console.log("new User ", newUser);
                          this.msgService.add({
                            key: 'remove',
                            severity: 'success',
                            detail: 'Role successfully removed from user ' + this.user.username,
                            sticky: true
                          });
                        });
                      },
                      reject: () => {
                        this.msgService.add({
                          key: 'reject',
                          severity: 'error',
                          detail: 'rejected',
                          sticky: true
                        });
                      }
                    });
                  }
                })
              }
            })
            break;

          case UserEvent.OPEN_PWD_CHANGE_UI:
            console.log(event1);
            this.user$ = this.userService.getUserById(this.userId);
            this.user$.subscribe({
              next: (value: AppUser) => {
                this.user = value;
                this.chgPwdForm = this.fb.group({
                  username1: [{ value: this.user.username, disbled: true }],
                  pwd: ['', Validators.required],
                  pwdNew: ['', [Validators.required, Validators.minLength(5)]],
                  pwdNewConfirm: ['', [Validators.required, Validators.minLength(5)]],
                });
              },
              error: (err) => {
                console.log(`error accured ${err}`);
              },
              complete: () => {
                console.log("observale complete emission");
              }
            })
            break;
        }
      },
      error: (err) => {
        alert(`an erron accured ${err}`);
      },
      complete: () => {

      }
    });
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
    this.userEventPublisher.publishUserEvent(UserEvent.MANAGE_USER);
  }
  onSubmit1() {
    this.userEventPublisher.publishUserEvent(UserEvent.CHANGE_PWD)
  }
}
