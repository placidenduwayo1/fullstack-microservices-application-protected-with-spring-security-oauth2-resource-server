import { inject } from "@angular/core";
import { CanActivateFn } from "@angular/router";
import { UserAuthenticationService } from "../rest-services/app-user-service/authentication.service";

export const authenticationGuardService: CanActivateFn = () => {

  const authService = inject(UserAuthenticationService);
  const jwtToken = authService.getToken();
  if (jwtToken) {
    if (!authService.getJwtExpiration(jwtToken)) {
      return true;
    }
    console.log('---------------------- token has expired-----------------------');
    authService.logout();
    return false;
  }
  else {
    authService.logout();
    return false;
  }
}

export const authorizationGuardService: CanActivateFn = () => {
  const authService = inject(UserAuthenticationService);
  const jwtToken = authService.getToken();
  const decodedJwt: any = authService.getDecodedJwt(jwtToken);
  const authorities = ['ADMIN','MANAGER','USER','HR']
  if (decodedJwt.scope.some((authority: string)=>authorities.includes(authority))) {
    return true;
  }
  return false;
}

