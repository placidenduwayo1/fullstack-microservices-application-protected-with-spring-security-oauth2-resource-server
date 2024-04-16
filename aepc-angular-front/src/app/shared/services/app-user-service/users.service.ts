import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { AppUser } from "../../models/user-auth/user.model";
import { myheaders } from "../rest-services/headers";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { RoleUserForm } from "./role-user-form.model";

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
    addRoleUser(roleUserForm: RoleUserForm): Observable<AppUser> {
        return this.httpClient.post<AppUser>(this.baseUrl + '/add-role-user', roleUserForm);
    }
    getAllRoles(): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/roles');
    }
    delete(idUserToDelete: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.baseUrl}/users/id/${idUserToDelete}`);
    }

    getUser(userId: any): Observable<AppUser> {
        return this.httpClient.get<AppUser>(`${this.baseUrl}/users/id/${userId}`);
    }

    removeRoleFromUser(roleUserForm: RoleUserForm): Observable<AppUser> {
        return this.httpClient.post<AppUser>(this.baseUrl + '/remove-role-user', roleUserForm);
    }
}