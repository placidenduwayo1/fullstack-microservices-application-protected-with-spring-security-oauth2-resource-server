import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DtoToken } from 'src/app/shared/models/user-auth/dto.token';
import { UserAuthenticationService } from 'src/app/shared/services/auth-service/authentication.service';

@Component({
  selector: 'app-login-component',
  templateUrl: './login-component.component.html',
  styleUrls: ['./login-component.component.scss']
})
export class LoginComponentComponent implements OnInit {
  loginForm!: FormGroup;
  constructor(private fb: FormBuilder,
    private authService: UserAuthenticationService,
    private router: Router) { }

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
  }

  onLogin() {
    let login = this.loginForm.get('login')?.value;
    let pwd = this.loginForm.get('pwd')?.value;
    let dtoToken: DtoToken = new DtoToken();
    dtoToken.username = login;
    dtoToken.pwd = pwd;
    dtoToken.grantType = 'generate-access-token-from-username-and-password';
    dtoToken.withRefreshToken = true;
    dtoToken.refreshToken = '';
    this.authService.login(dtoToken).subscribe({
      next: (data: any) => {
          console.log(data);
          this.router.navigateByUrl("/session")
      },
      error: err => {
          console.log('username or password invalid: authentication error', err);
          this.router.navigateByUrl("/login")

      }
  });;
  }
}




