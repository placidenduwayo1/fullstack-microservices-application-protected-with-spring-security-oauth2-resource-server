import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginLogoutRoutingModule } from './login-routing.module';
import { LoginComponentComponent } from './login/login-component.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import { MessagesModule } from 'primeng/messages';
import { MessageService } from 'primeng/api';
import { CardModule } from 'primeng/card';
import { LogoutComponent } from './logout/logout.component';

@NgModule({
  declarations: [
    LoginComponentComponent,
    LogoutComponent
  ],
  imports: [
    CommonModule,
    LoginLogoutRoutingModule,
    ReactiveFormsModule,
    PasswordModule,
    FormsModule,
    InputTextModule,
    MessagesModule,
    CardModule
  ],
  providers:[MessageService]
})
export class LoginLogoutModule { }
