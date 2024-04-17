import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { AppUser } from "../../models/user-auth/user.model";
import { myheaders } from "../rest-services/headers";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { RoleUserForm } from "./role-user-form.model";
import { AppRole } from "../../models/user-auth/role.model";

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

    getRoleByName(role: string): Observable<AppRole>{
        return this.httpClient.get<AppRole>(this.baseUrl+`/roles/${role}`);
    }

}