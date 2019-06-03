import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material';
import { environment } from '../../environments/environment';
import { User } from '../domain/User';

@Injectable()
export class AuthService {

  loggedInUser: User;

  constructor(private router: Router,
              private http: HttpClient,
              private snackBar: MatSnackBar) {
  }

  login(username: string, password: string) {
    this.http.post(`${environment.baseUrl}/login`, {username, password})
    .subscribe((loggedInUser: User) => {
      console.log('logged in ', loggedInUser);

      this.loggedInUser = loggedInUser;
      localStorage.setItem('jwt', loggedInUser.jwt);

      this.router.navigate(['/']);
    }, error => {
      console.log(error);
      this.snackBar.open('Incorrect username or password.', 'OK');
    });
  }

  signup(username: any, password: any) {
    this.http.post(`${environment.baseUrl}/signup`, {username, password})
    .subscribe((result: any) => {
      console.log('signup: ', result);

      this.router.navigate(['/login']);
    }, error => {
      console.log(error);
      this.snackBar.open('Username already taken', 'OK');
    });
  }

  logout() {
    this.loggedInUser = null;
    localStorage.removeItem("jwt");

    this.router.navigate(['/login']);
  }

}
