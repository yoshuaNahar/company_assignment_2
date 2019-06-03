import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { User } from '../domain/User';
import { Group } from '../domain/Group';

@Injectable()
export class DashboardService {

  constructor(private http: HttpClient) {
  }

  getUserWithJwt(): Observable<User> {
    const jwt = localStorage.getItem('jwt');

    return this.http.get<User>(`${environment.baseUrl}/api/user`,
      {headers: {"Authorization": `Bearer ${jwt}`}}).pipe();
  }

  addGroup(groupName: string): Observable<{id, name, userIds, userNames}> {
    const jwt = localStorage.getItem('jwt');

    return this.http.post<any>(`${environment.baseUrl}/api/group`,
      {name: groupName},
      {headers: {"Authorization": `Bearer ${jwt}`}}).pipe();
  }

}
