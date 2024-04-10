import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { jwtDecode } from "jwt-decode";
import { Observable, tap } from "rxjs";
import { DtoToken } from "src/app/shared/models/user-auth/dto.token";
import { environment } from "src/environments/environment";

@Injectable({ providedIn: 'root' })
export class UserAuthenticationService {

    private baseUrl!: string;
    
    constructor(private httpClient: HttpClient, private router: Router) {
        this.baseUrl = environment.gatewayService + "/api-auth";
    }

    private setToken(token: string) {
        localStorage.setItem('token', token)
    }

    public getToken(): string | null {
        const token = localStorage.getItem('token');
        console.log("access-token saved into local storage ", localStorage.getItem('token'));
        return token;
    }

    public login(dtoToken: DtoToken){
        return this.httpClient.post<DtoToken>(this.baseUrl + "/login", dtoToken).pipe(
            tap((data: any)=>{
                console.log('authentication service logined ', data);
                const access_token: string = data['access-token'];
                this.setToken(access_token);

                const decodedJwt: any= jwtDecode(access_token);
                console.log("decoded jwt ", decodedJwt);
                console.log("username",decodedJwt.sub);
                console.log("user roles",decodedJwt.scope);
            })
        );
    }

    public isLoggedIn(): boolean {
        const token = this.getToken();
        if (token) {
            return true;
        }
        return false;
    }

    public logout() {
        localStorage.removeItem('token');
        console.log("access-token removed from local storage ")
    }

    public getUsers(): Observable<any[]> {
        return this.httpClient.get<any[]>(this.baseUrl + "/users");
    }
}

