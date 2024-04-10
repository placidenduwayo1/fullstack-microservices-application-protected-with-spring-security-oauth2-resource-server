import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { UserAuthenticationService } from 'src/app/shared/services/auth-service/authentication.service';


@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  items!: MenuItem[];
  activeItem!: MenuItem;

  constructor(private router: Router, private auth: UserAuthenticationService) {
  }

  ngOnInit(): void {
    this.items = [
      {
        label: 'Home', icon: 'pi pi-fw pi-home',
        command: () => this.router.navigateByUrl('session/accueil')
      },
      {
        label: 'api-address',
        items: [
          {
            label: 'addresses', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl('session/addresses-management')

          },
          {
            label: 'create', icon: 'pi pi-plus-circle',
            command: () => this.router.navigateByUrl('session/addresses-management/address-form-create')
          }
        ]
      },
      {
        label: 'api-company',
        items: [
          {
            label: 'companies', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl('session/companies-management')
          },
          {
            label: 'create', icon: 'pi pi-plus-circle',
            command: () => this.router.navigateByUrl('session/companies-management/company-create')
          }
        ]
      },
      {
        label: 'api-employee',
        items: [
          {
            label: 'employees', icon: 'pi pi-list',
            command: () => this.router.navigate(['session/employees-management'])
          },
          {
            label: 'create', icon: 'pi pi-plus-circle',
            command: () => this.router.navigateByUrl("session/employees-management/employee-form-create")
          }
        ]
      },
      {
        label: 'api-project',
        items: [
          {
            label: 'projects', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl('session/projects-management')
          },
          {
            label: 'create', icon: 'pi pi-plus-circle',
            command: () => this.router.navigateByUrl('session/projects-management/project-create')
          }
        ]
      },
      {
        label: 'Users', icon: 'pi pi-list',
        items: [
          { label: 'users', icon: 'pi pi-user', command: () => this.router.navigateByUrl("session/users-management") },
          {label: 'users', icon:'pi pi-plus-circle', command: ()=>console.log('create new user')}
        ]
      },
      {
        label: 'logout', icon: 'my-margin-left pi pi-fw pi-sign-out',
        command: () => this.logout()
      }

    ];

    this.activeItem = this.items[0];
  }

  logout() {
    this.auth.logout();
    this.router.navigateByUrl("login")
  }

  onGetUsers() {
    this.auth.getUsers().subscribe(data => {
      console.log(data)
    })
  }


}
