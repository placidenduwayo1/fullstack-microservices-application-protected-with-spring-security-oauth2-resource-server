import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { AppRole } from "src/app/shared/models/user-auth/role.model";
import { AppUser } from "src/app/shared/models/user-auth/user.model";
import { myheaders } from "../headers";

@Injectable({ providedIn: 'root' })
export class UsersManagementService {
    private baseUrl!: string;
    constructor() {
        this.baseUrl = environment.gatewayService + "/api-auth";
    }

    private httpClient = inject(HttpClient);
    createUser(user: AppUser): Observable<AppUser> {
        return this.httpClient.post<AppUser>(this.baseUrl + '/users', user, { headers: myheaders });
    }
    getUsers(): Observable<Array<any>> {
        return this.httpClient.get<any[]>(this.baseUrl + "/users");
    }
    addRoleUser(username: string, role: string): Observable<AppUser> {
        return this.httpClient.post<AppUser>(this.baseUrl + '/add-role-user', { username, role }, { headers: myheaders });
    }
    getAllRoles(): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/roles');
    }
    delete(idUserToDelete: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.baseUrl}/users/id/${idUserToDelete}`);
    }

    getUserById(userId: any): Observable<AppUser> {
        return this.httpClient.get<AppUser>(`${this.baseUrl}/users/id/${userId}`);
    }

    removeRoleFromUser(username: string, role: string): Observable<AppUser> {
        return this.httpClient.post<AppUser>(this.baseUrl + '/remove-role-user', { username, role }, { headers: myheaders });
    }

    getRoleByName(role: string): Observable<AppRole> {
        return this.httpClient.get<AppRole>(this.baseUrl + `/roles/${role}`);
    }
    changeUserPassword(username: string, currentPwd: string, pwdNew: string, PwdNewConfirm: string): Observable<AppUser> {
        return this.httpClient.post<AppUser>(this.baseUrl + '/change-pwd',
            { username, currentPwd, pwdNew, PwdNewConfirm });
    }
    createNewRole(role: string): Observable<AppRole> {
        return this.httpClient.post<AppRole>(this.baseUrl + '/roles', { role }, { headers: myheaders });
    }
}