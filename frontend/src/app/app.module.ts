import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MaterialModule } from './material.module';
import { ChatWindowComponent } from './dashboard/chat-window/chat-window.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AuthService } from './service/auth.service';
import { AuthGuard } from './auth-guard';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ChatWindowMembersModal } from './dashboard/chat-window/chat-window-members/chat-window-members.modal';
import { AddGroupModal } from './dashboard/add-group/add-group.modal';
import { InjectableRxStompConfig, RxStompService, rxStompServiceFactory } from '@stomp/ng2-stompjs';
import { stompConfig } from './stomp.config';
import { HttpClientModule } from '@angular/common/http';
import { SignupComponent } from './signup/signup.component';
import { DashboardService } from './service/dashboard.service';

@NgModule({
  declarations: [
    AppComponent,
    ChatWindowComponent,
    SignupComponent,
    LoginComponent,
    DashboardComponent,
    ChatWindowMembersModal,
    AddGroupModal,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MaterialModule,
  ],
  providers: [
    AuthService,
    AuthGuard,
    DashboardService,
    // Stomp related
    {
      provide: InjectableRxStompConfig,
      useValue: stompConfig
    },
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory,
      deps: [InjectableRxStompConfig]
    }
  ],
  entryComponents: [
    ChatWindowMembersModal,
    AddGroupModal,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
