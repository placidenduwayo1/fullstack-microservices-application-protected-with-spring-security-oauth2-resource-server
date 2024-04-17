import { inject } from "@angular/core";
import { ActivatedRoute, ResolveFn } from "@angular/router";
import {  } from "../../app-user-service/authentication.service";
import { UsersManagementService } from "../../app-user-service/users.service";
import { AppUser } from "src/app/shared/models/user-auth/user.model";

export const GetAllUsersResolve : ResolveFn<Array<AppUser>> = ()=>{
    return inject(UsersManagementService).getUsers();
}

export const GetUserResolve : ResolveFn<AppUser> = ()=>{
    const userId = inject(ActivatedRoute).snapshot.params['userId'];
    return inject(UsersManagementService).getUserById(userId);
}