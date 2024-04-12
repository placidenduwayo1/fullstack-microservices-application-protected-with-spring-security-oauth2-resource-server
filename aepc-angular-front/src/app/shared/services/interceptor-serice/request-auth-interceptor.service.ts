import { HttpHandler, HttpHeaders, HttpInterceptor, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { UserAuthenticationService } from "../auth-service/authentication.service";

@Injectable()
export class HttpRequestAuthenticationInterceptor implements HttpInterceptor {

    intercept(inputRequest: HttpRequest<any>, next: HttpHandler) {
        //on loging, jwt token is not yet generated
        if (inputRequest.url.includes('/api-auth/login')){
            return next.handle(inputRequest);
        }
        else {
            let jwtToken = inject(UserAuthenticationService).getToken();
            let headers = new HttpHeaders().append('Authorization', `Bearer ${jwtToken}`)
            let newRequest = inputRequest.clone({ headers: headers });
            return next.handle(newRequest);
        }
    }
}