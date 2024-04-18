import { Component, inject } from '@angular/core';
import { UserAuthenticationService } from 'src/app/shared/services/rest-services/app-user-service/authentication.service';

@Component({
  selector: 'app-user-u-print',
  templateUrl: './user-u-print.component.html',
  styleUrls: ['./user-u-print.component.scss']
})
export class UserUPrintComponent {
  private userAuthService = inject(UserAuthenticationService);
  decodedJwt = this.userAuthService.getDecodedJwt(this.userAuthService.getToken());
  formatedDecodedJwt = {
    "iss": this.decodedJwt.iss,
    "username": this.decodedJwt.sub,
    "exp": new Date(this.decodedJwt.exp * 1000),
    "iat": new Date(this.decodedJwt.iat * 1000),
    "roles": this.decodedJwt.scope
  }
}
