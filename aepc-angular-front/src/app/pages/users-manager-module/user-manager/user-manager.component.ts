import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserEvent } from 'src/app/shared/models/events/events.model';
import { AppUser } from 'src/app/shared/models/user-auth/user.model';
import { UsersManagementService } from 'src/app/shared/services/app-user-service/users.service';
import { UserEventServicePublisher } from 'src/app/shared/services/publisher-events-services/user.events.publisher';

@Component({
  selector: 'app-user-manager',
  templateUrl: './user-manager.component.html',
  styleUrls: ['./user-manager.component.scss']
})
export class UserManagerComponent implements OnInit {
  private activatedRoute = inject(ActivatedRoute);
  private userService = inject(UsersManagementService);
  private userEventPublisher = inject(UserEventServicePublisher);
  users!: Array<any>
  ngOnInit(): void {
    this.activatedRoute.data.subscribe((data)=>{
      this.users = data['getAllUsers'];
    });

    this.userEventPublisher.userEventObservable.subscribe((event: UserEvent)=>{
      if(event==UserEvent.REFRESH){
        this.userService.getUsers().subscribe((data:Array<AppUser>)=>{
          this.users=data;
        })
      }
    })

  }
}
