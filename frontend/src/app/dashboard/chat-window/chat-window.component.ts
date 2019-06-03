import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ChatWindowMembersModal } from './chat-window-members/chat-window-members.modal';
import { RxStompService } from '@stomp/ng2-stompjs';
import { Group } from '../../domain/Group';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-chat-window',
  templateUrl: './chat-window.component.html',
  styleUrls: ['./chat-window.component.scss']
})
export class ChatWindowComponent implements OnInit, OnChanges {

  text;

  @Input()
  group: Group;

  subscription: Subscription;

  constructor(public dialog: MatDialog,
              private rxStompService: RxStompService) {
  }

  ngOnInit(): void {

  }

  ngOnChanges(changes): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }

    this.subscription = this.rxStompService.watch(`/topic/${this.group.name.replace(" ", "")}`).subscribe(val => {
      console.log(val);
      console.log(val.body);
      this.group.messages.push(JSON.parse(val.body));
    });
  }

  onSendMessage() {
    const jwt = localStorage.getItem('jwt');

    const message = {message: this.text, jwt, groupName: this.group.name};
    this.rxStompService.publish({destination: '/app/user', body: JSON.stringify(message)});
    this.text = '';
  }

  openMembersModal(): void {
    const dialogRef = this.dialog.open(ChatWindowMembersModal, {
      width: '250px',
      data: {group: this.group}
    });

    dialogRef.afterClosed().subscribe(() => {
      console.log('The dialog was closed');
    });
  }

}
