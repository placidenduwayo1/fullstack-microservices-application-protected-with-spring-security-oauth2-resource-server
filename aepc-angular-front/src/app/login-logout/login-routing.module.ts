import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponentComponent } from "./login/login-component.component";
import { LogoutComponent } from "./logout/logout.component";

const routes: Routes = [
    {
        path:'login', component: LoginComponentComponent
    },
    {
        path:'logout', component: LogoutComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LoginLogoutRoutingModule {}

