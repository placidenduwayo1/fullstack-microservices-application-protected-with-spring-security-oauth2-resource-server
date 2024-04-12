import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { userInfo } from 'os';
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

  private router = inject(Router);
  private userAUthService = inject(UserAuthenticationService);

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
            command: () => this.router.navigateByUrl('session/addresses-management'),
            visible: this.authorized
          },
          {
            label: 'create', icon: 'pi pi-plus-circle',
            command: () => this.router.navigateByUrl('session/addresses-management/address-form-create'),
            visible: this.authorized
          }
        ]
      },
      {
        label: 'api-company',
        items: [
          {
            label: 'companies', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl('session/companies-management'),
            visible: this.authorized
          },
          {
            label: 'create', icon: 'pi pi-plus-circle',
            command: () => this.router.navigateByUrl('session/companies-management/company-create'),
            visible: this.authorized
          }
        ]
      },
      {
        label: 'api-employee',
        items: [
          {
            label: 'employees', icon: 'pi pi-list',
            command: () => this.router.navigate(['session/employees-management']),
            visible: this.authorized
          },
          {
            label: 'create', icon: 'pi pi-plus-circle',
            command: () => this.router.navigateByUrl("session/employees-management/employee-form-create"),
            visible: this.authorized
          }
        ]
      },
      {
        label: 'api-project',
        items: [
          {
            label: 'projects', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl('session/projects-management'),
            visible: this.authorized
          },
          {
            label: 'create', icon: 'pi pi-plus-circle',
            command: () => this.router.navigateByUrl('session/projects-management/project-create'),
            visible: this.authorized
          }
        ]
      },
      {
        label: 'Users',
        items: [
          {
            label: `${this.userInfo.username}`, icon:'pi pi-user',
            command:()=>this.router.navigateByUrl('session/users-management/user-u')
          },
          { 
            label: 'users', icon: 'pi pi-user',
            command: () => this.router.navigateByUrl('session/users-management'),
            visible: this.authorized
          },
          {
            label: 'users', icon:'pi pi-plus-circle',
            command: ()=>this.router.navigateByUrl('session/users-management/user-create'),
            visible: this.authorized
          },
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
    this.userAUthService.logout();
    this.router.navigateByUrl("login")
  }

  jwtToken: any = this.userAUthService.getToken();
  decodedJwt: any = this.userAUthService.getDecodedJwt(this.jwtToken);
  userInfo : any = {
    "username": this.decodedJwt.sub,
    "roles": this.decodedJwt.scope
  }
  authorized:boolean = this.decodedJwt.scope.includes("ADMIN","HR");
}