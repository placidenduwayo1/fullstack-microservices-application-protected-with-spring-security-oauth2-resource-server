import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-manager',
  templateUrl: './user-manager.component.html',
  styleUrls: ['./user-manager.component.scss']
})
export class UserManagerComponent implements OnInit {
  private activatedRoute = inject(ActivatedRoute);
  users!: Array<any>
  ngOnInit(): void {
    this.activatedRoute.data.subscribe((data)=>{
      this.users = data['getAllUsers'];
      console.log(this.users)
    })
  }
}
