import { inject } from "@angular/core";
import { ResolveFn } from "@angular/router";
import { UserAuthenticationService } from "../../auth-service/authentication.service";

export const usersRouteResolve : ResolveFn<Array<any>> = ()=>{
    return inject(UserAuthenticationService).getUsers();
}