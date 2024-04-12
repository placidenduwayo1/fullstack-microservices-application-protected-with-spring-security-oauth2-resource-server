import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { UserAuthenticationService } from "../auth-service/authentication.service";

export const authenticationGuardService: CanActivateFn = () => {

  const authService = inject(UserAuthenticationService);
  const jwtToken = authService.getToken();
  if (jwtToken) {
    if (!authService.getJwtExpiration(jwtToken)) {
      return true;
    }
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
  const router = inject(Router);
  const jwtToken = authService.getToken();
  const decodedJwt: any = authService.getDecodedJwt(jwtToken);
  if (decodedJwt.scope.includes("ADMIN") || decodedJwt.scope.includes("HR")) {
    return true;
  }
  console.log("you do not have right to do this task")
  router.navigateByUrl('session/unauthorized');
  return false;
}

