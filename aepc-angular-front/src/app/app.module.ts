import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MyMenuModule } from './pages/menu-module/menu.module';
import { LoginModule } from './login/login.module';
import { AppSessionComponent } from './pages/app-session/app-session.component';
import { HttpRequestAuthenticationInterceptor } from './shared/services/interceptor-serice/request-auth-interceptor.service';


@NgModule({
  declarations: [AppComponent, AppSessionComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    BrowserAnimationsModule,
    LoginModule,
    MyMenuModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpRequestAuthenticationInterceptor, multi: true }
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
