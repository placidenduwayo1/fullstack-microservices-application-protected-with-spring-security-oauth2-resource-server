import { Component, Inject, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { UserEvent } from 'src/app/shared/models/events/events.model';
import { DtoToken } from 'src/app/shared/models/user-auth/dto.token';
import { UserAuthenticationService } from 'src/app/shared/services/app-user-service/authentication.service';
import { UserEventServicePublisher } from 'src/app/shared/services/publisher-events-services/user.events.publisher';

@Component({
  selector: 'app-login-component',
  templateUrl: './login-component.component.html',
  styleUrls: ['./login-component.component.scss']
})
export class LoginComponentComponent implements OnInit {
  loginForm!: FormGroup;
  private fb = inject(FormBuilder);
  private authService = inject(UserAuthenticationService);
  private router = inject(Router);
  private msgService = inject(MessageService);
  private userEventPublisher = inject(UserEventServicePublisher);

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      login: ['', Validators.required],
      pwd: ['', Validators.required]
    });

    /*s'il est déjà loggé c'est à dire que le token est deja setté dans le local storage:
      localStorage.setItem('token', token), même s'il revient on login, du moment qu'il n'a pas cliqué sur logout,
      il reste connecté et sera toujour redirgé vers la session*/
    if (this.authService.isLoggedIn()) {
      this.router.navigate(["session"]);
    }

    this.userEventPublisher.userEventObservable.subscribe((event: UserEvent) => {
      if (event == UserEvent.LOGIN) {
        console.log(event);
        let dtoToken: DtoToken = new DtoToken();
        dtoToken.username = this.loginForm.get('login')?.value;;
        dtoToken.pwd = this.loginForm.get('pwd')?.value;;
        dtoToken.grantType = 'generate-access-token-from-username-and-password';
        dtoToken.withRefreshToken = true;
        dtoToken.refreshToken = '';

        this.authService.login(dtoToken).subscribe({
          next: (data: any) => {
            console.log(data);
            this.router.navigateByUrl("/session")
          },
          error: () => {
            this.msgService.add({
              key: 'auth-error',
              detail: 'username and/or password invalid',
              sticky: true,
              severity: 'error'
            })
            this.router.navigateByUrl("/login")

          }
        });;
      }
    })
  }

  onLogin() {
    this.userEventPublisher.publishUserEvent(UserEvent.LOGIN);
  }
}




