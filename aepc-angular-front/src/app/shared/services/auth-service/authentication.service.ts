import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Router } from "@angular/router";
import { jwtDecode } from "jwt-decode";
import { Observable, tap } from "rxjs";
import { DtoToken } from "src/app/shared/models/user-auth/dto.token";
import { environment } from "src/environments/environment";

@Injectable({ providedIn: 'root' })
export class UserAuthenticationService {

    private baseUrl!: string;

    constructor() {
        this.baseUrl = environment.gatewayService + "/api-auth";
    }

    private httpClient = inject(HttpClient);
    private router = inject(Router);

    private setToken(jwtToken: string) {
        localStorage.setItem('token', jwtToken)
    }

    public getToken(): string | null {
        return localStorage.getItem('token');
    }

    public login(dtoToken: DtoToken) {
        return this.httpClient.post<DtoToken>(this.baseUrl + "/login", dtoToken).pipe(
            tap((data: any) => {
                const access_token: string = data['access-token'];
                this.setToken(access_token);
                const decodedJwt: any = jwtDecode(access_token);
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
        this.router.navigateByUrl('');
    }

    public getUsers(): Observable<Array<any>> {
        return this.httpClient.get<any[]>(this.baseUrl + "/users");
    }

    public getJwtExpiration(jwtToken: any): boolean {
        // install first jwt-decode: npm install --save jwt-decode to decode jwt token into json format
        let decodedJwt: any = jwtDecode(jwtToken);
        if (decodedJwt.exp * 1000 < Date.now()) {
            return true;
        }
        return false;
    }

    public getDecodedJwt(jwtToken: any): any {
        return jwtDecode(jwtToken);
    }
}