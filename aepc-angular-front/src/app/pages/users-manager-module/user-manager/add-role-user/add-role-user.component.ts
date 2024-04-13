import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AppUser } from 'src/app/shared/models/user-auth/user.model';
import { UsersManagementService } from 'src/app/shared/services/app-user-service/users.service';

@Component({
  selector: 'app-add-role-user',
  templateUrl: './add-role-user.component.html',
  styleUrls: ['./add-role-user.component.scss']
})
export class AddRoleUserComponent implements OnInit{
  private activatedRoute = inject(ActivatedRoute);
  userId!: number;
  private userService = inject(UsersManagementService);
  userForm!: FormGroup;
  private fb = inject(FormBuilder);

  ngOnInit(): void {
    this.userId =  this.activatedRoute.snapshot.params['userId'];
    this.userService.getUser(this.userId).subscribe((user: AppUser)=>{
      console.log(user);
      this.userForm = this.fb.group({
        userId: [user.userId, Validators.required],
        firstname: [user.firstname, [Validators.required, Validators.minLength(2)]],
        lastname: [user.lastname, [Validators.required]]
      });
    })
    
  }


}
