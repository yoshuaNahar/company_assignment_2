import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-members-modal',
  templateUrl: './chat-window-members.modal.html',
})
export class ChatWindowMembersModal implements OnInit {

  constructor(public dialogRef: MatDialogRef<ChatWindowMembersModal>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
  }

  back(): void {
    this.dialogRef.close();
  }

}
