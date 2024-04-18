import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Observable } from 'rxjs';
import { UserEvent } from 'src/app/shared/models/events/events.model';
import { AppRole } from 'src/app/shared/models/user-auth/role.model';
import { AppUser } from 'src/app/shared/models/user-auth/user.model';
import { UserEventServicePublisher } from 'src/app/shared/services/publisher-events-services/user.events.publisher';
import { UsersManagementService } from 'src/app/shared/services/rest-services/app-user-service/users.service';

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
  roleForm!: FormGroup;

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
                  role: ['', [Validators.required, Validators.minLength(2)]]
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
                        if (roles.findIndex((value: AppRole) => value.role == role) > 0) {
                          this.msgService.add({
                            key: 'add',
                            severity: 'error',
                            sticky: true,
                            detail: `User ${this.user.username} has already role ${role}`
                          });
                        }
                        else {
                          this.userService.addRoleUser(username, role).subscribe(newUser => {
                            console.log("new User ", newUser);
                            this.msgService.add({
                              key: 'add',
                              severity: 'success',
                              detail: `Role successfully added to user  ${this.user.username}`,
                              sticky: true
                            });
                          });
                        }
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
                  role: ['', [Validators.required, Validators.minLength(2)]]
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
                      message: 'Confirm remove Role from user ' + this.user.username,
                      accept: () => {
                        const username = this.roleUserForm.getRawValue().username;
                        const role = this.roleUserForm.value.role;
                        const roles = this.user.roles;
                        if (roles.findIndex((item: AppRole) => item.role == role) < 0) {
                          this.msgService.add({
                            key: 'remove',
                            severity: 'error',
                            sticky: true,
                            detail: `Role ${role} for user ${this.user.username} not found exits`
                          });
                        }
                        else {
                          this.userService.removeRoleFromUser(username, role).subscribe(newUser => {
                            console.log("new User ", newUser);
                            this.msgService.add({
                              key: 'remove',
                              severity: 'success',
                              detail: `Role successfully removed from user  ${this.user.username}`,
                              sticky: true
                            });
                          });
                        }
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
                console.log('------------pwd ', this.user.pwd);
                this.chgPwdForm = this.fb.group({
                  username1: [{ value: this.user.username, disabled: true }],
                  currentPwd: ['', Validators.required],
                  pwdNew: ['', [Validators.required, Validators.minLength(5)]],
                  pwdNewConfirm: ['', [Validators.required, Validators.minLength(5)]],
                });
              },
              error: (err) => {
                console.log(`error of opening pwd change ui accured ${err}`);
              },
              complete: () => {
                console.log("observale complete emission");
                this.userEventPublisher.userEventObservable.subscribe((event: UserEvent) => {
                  if (event == UserEvent.SAVE_CHANGE_PWD) {
                    console.log(this.chgPwdForm.value)
                    const username = this.chgPwdForm.getRawValue().username1;
                    const pwd0 = this.chgPwdForm.value.currentPwd;
                    const pwd1 = this.chgPwdForm.value.pwdNew;
                    const pwd2 = this.chgPwdForm.value.pwdNewConfirm;

                    if (pwd1 !== pwd2) {
                      this.msgService.add({
                        key: 'chg-pwd0',
                        severity: 'error',
                        detail: 'New Password and Confirm Password not match Exception !!!!!!!!!!!!!!!!!!',
                        sticky: true
                      });
                    }
                    else {
                      this.userService.changeUserPassword(username, pwd0, pwd1, pwd2).subscribe((appUser: AppUser) => {
                        console.log(appUser);
                        this.msgService.add({
                          key: 'pwdChanged',
                          severity: 'success',
                          detail: `Password successfully changed for ${this.user.username}`,
                          sticky: true
                        });
                      });
                    }
                  }
                });
              }
            });
            break;

          case UserEvent.OPEN_ROLE_UI:
            console.log(event1);
            this.roleForm = this.fb.group({
              role: ['', [Validators.required, Validators.minLength(2)]]
            });
            this.userEventPublisher.userEventObservable.subscribe({
              next: (value: UserEvent) => {
                console.log(value);
                let roles: Array<AppRole> | any = null;
                this.userService.getAllRoles().subscribe((data: Array<AppRole>) => {
                  roles = data;
                  console.log(roles);
                });
                if (value == UserEvent.SAVE_NEW_ROLE) {
                  const role: string = this.roleForm.value.role;
                  this.confirmService.confirm({
                    acceptLabel: 'Yes',
                    rejectLabel: 'No',
                    message: `Confirm create new role ${role}`,
                    accept: () => {

                      if (roles.findIndex((item: AppRole) => item.role == role)>0) {
                        this.msgService.add({
                          key: 'add-role',
                          severity: 'error',
                          sticky: true,
                          detail: `Role ${role} already exists in store Exception`
                        });
                      }
                      else {
                        const newRole$: Observable<AppRole> = this.userService.createNewRole(role);
                        newRole$.subscribe((appRole: AppRole) => {
                          console.log(appRole);
                          this.msgService.add({
                            key: 'add-role',
                            severity: 'success',
                            sticky: true,
                            detail: `Role ${role} created successfully`
                          });
                          return appRole;
                        });
                      }
                    },
                    reject: () => {
                      this.msgService.add({
                        key: 'reject',
                        severity: 'error',
                        sticky: true,
                        detail: `Reject role creation`
                      });
                      return null;
                    }
                  });
                }
              },
              error: (err) => {
                console.log(`error of saving new role accured ${err}`);
              },
              complete: () => {
                console.log('emission completed');
              }
            })
            break;
        }
      },
      error: (err) => {
        console.log(`an error about user management accured ${err}`);
      },
      complete: () => {
        console.log('finished to emit event');
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
  onSubmitChangePwd() {
    this.userEventPublisher.publishUserEvent(UserEvent.SAVE_CHANGE_PWD)
  }

  openRoleUI() {
    this.userEventPublisher.publishUserEvent(UserEvent.OPEN_ROLE_UI)
  }
  onSubmitAddRole() {
    this.userEventPublisher.publishUserEvent(UserEvent.SAVE_NEW_ROLE)
  }
}
