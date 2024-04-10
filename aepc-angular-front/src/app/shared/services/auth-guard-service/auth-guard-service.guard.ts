import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { UserAuthenticationService } from "../auth-service/authentication.service";
import { jwtDecode } from "jwt-decode";

export const authenticationGuardService: CanActivateFn = () => {
  const router: Router = inject(Router);
  let token = inject(UserAuthenticationService).getToken();
  if (token) {
    let decodedJwt = jwtDecode(token);
    const expiredToken = (decodedJwt && decodedJwt.exp) ? decodedJwt.exp < Date.now()/1000: false ;
    if(expiredToken){
      console.log("--------------------------- token is expired");
      router.navigateByUrl('');
      return false;
    }
    else {
      return true;
    }
  }
  else {
    router.navigateByUrl('');
    return false;
  }
}