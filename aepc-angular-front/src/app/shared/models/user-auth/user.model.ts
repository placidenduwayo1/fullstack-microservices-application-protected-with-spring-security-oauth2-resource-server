import { AppRole } from "./role.model";

export class AppUser {
    userId!: number;
    username!: string;
    firstname!: string;
    lastname!: string;
    pwd!: string;
    pwdConfirm!: string;
    roles!: Array<AppRole>;
}