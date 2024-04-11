import { Component, OnInit, inject } from '@angular/core';
import { UserAuthenticationService } from 'src/app/shared/services/auth-service/authentication.service';

@Component({
  selector: 'app-user-manager',
  templateUrl: './user-manager.component.html',
  styleUrls: ['./user-manager.component.scss']
})
export class UserManagerComponent implements OnInit {
  private usersAuthService = inject(UserAuthenticationService);
  users!: Array<any>
  ngOnInit(): void {
    this.usersAuthService.getUsers().subscribe((data:Array<any>)=>{
      this.users = data;
      console.log(this.users)
    })
  }
}
