import { inject } from "@angular/core";
import {  ResolveFn } from "@angular/router";
import { AppUser } from "src/app/shared/models/user-auth/user.model";
import { UsersManagementService } from "../../rest-services/app-user-service/users.service";
import { AppRole } from "src/app/shared/models/user-auth/role.model";

export const GetAllUsersResolve : ResolveFn<Array<AppUser>> = ()=>{
    return inject(UsersManagementService).getUsers();
}