import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { AddGroupModal } from './add-group/add-group.modal';
import { AuthService } from '../service/auth.service';
import { Group } from '../domain/Group';
import { User } from '../domain/User';
import { DashboardService } from '../service/dashboard.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  user: User;
  selectedGroup: Group;

  constructor(private dialog: MatDialog,
              private authService: AuthService,
              private dashboardService: DashboardService,
              private router: Router) {
  }

  ngOnInit() {
    this.user = this.authService.loggedInUser;
    if (this.user == null) {
      this.dashboardService.getUserWithJwt().subscribe(loggedInUser => {
        console.log(loggedInUser);
        this.user = loggedInUser;
        this.authService.loggedInUser = loggedInUser;
        this.selectedGroup = this.user.groups[0];
      }, (error) => {
        console.log(error);
        this.router.navigate(['/login']);
      });
    } else {
      this.selectedGroup = this.user.groups[0];
    }
  }

  addGroupModal(): void {
    const dialogRef = this.dialog.open(AddGroupModal, {
      width: '250px',
      data: {name: ''}
    });

    dialogRef.afterClosed().subscribe(groupName => {
      console.log('The dialog was closed', groupName);
      this.dashboardService.addGroup(groupName)
      .subscribe(createdGroup => {
        const group = {id: createdGroup.id, name: createdGroup.name, users: [], messages: []};
        createdGroup.userNames.forEach(name => {
          group.users.push({name})
        });

        this.user.groups.push(group);
      }, error => {
        console.log(error);
      });
    });
  }

  logout() {
    this.authService.logout();
  }

}
