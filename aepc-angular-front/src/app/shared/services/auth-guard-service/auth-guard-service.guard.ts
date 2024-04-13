import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { UserAuthenticationService } from "../app-user-service/authentication.service";

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
  if (decodedJwt.scope.includes("ADMIN") || decodedJwt.scope.includes("HR")) {
    return true;
  }
  return false;
}

