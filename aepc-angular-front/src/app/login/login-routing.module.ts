import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponentComponent } from "./login-component/login-component.component";

const routes: Routes = [
    {
        path:'login', 
        component: LoginComponentComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LoginRoutingModule {}

