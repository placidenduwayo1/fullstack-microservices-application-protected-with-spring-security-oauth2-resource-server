import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { UserEvent } from 'src/app/shared/models/events/events.model';
import { AppUser } from 'src/app/shared/models/user-auth/user.model';
import { UserAuthenticationService } from 'src/app/shared/services/app-user-service/authentication.service';
import { UsersManagementService } from 'src/app/shared/services/app-user-service/users.service';
import { UserEventServicePublisher } from 'src/app/shared/services/publisher-events-services/user.events.pyblisher';

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.scss']
})
export class UserCreateComponent implements OnInit {
  userForm!: FormGroup;
  private fb = inject(FormBuilder);
  private userEventPublisher = inject(UserEventServicePublisher);
  private msgService = inject(MessageService);
  private confirmService = inject(ConfirmationService);
  private userService = inject (UsersManagementService);

  ngOnInit(): void {
    this.userForm = this.fb.group({
      userId: ['', Validators.required],
      firstname: ['', [Validators.required, Validators.minLength(2)]],
      lastname: ['', [Validators.required, Validators.minLength(2)]],
      pwd: ['', Validators.required],
      pwdConfirm: ['', Validators.required]
    });

    this.userEventPublisher.userEventObservable.subscribe((event: UserEvent) => {
      if (event == UserEvent.SAVE_USER) {
        console.log(event);
        this.confirmService.confirm({
          acceptLabel: 'Yes',
          rejectLabel: 'No',
          message: 'Confirm user creation',

          accept: () => {
            this.userService.createUser(this.userForm.value).subscribe((user:AppUser)=>{
              this.msgService.add({
                key: 'create',
                severity:'success',
                detail: 'user created successfully',
                sticky: true
              });
              return user;
            });
          },
          reject: ()=>{
            this.msgService.add({
              key: 'rejected',
              severity:'error',
              detail: 'user creation rejected',
              sticky: true
            });
          }
        });
      }
    });
  }

  onCreate() {
    this.userEventPublisher.publishUserEvent(UserEvent.SAVE_USER);
  }
}
