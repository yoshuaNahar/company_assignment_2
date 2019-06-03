import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-add-group',
  templateUrl: './add-group.modal.html',
})
export class AddGroupModal implements OnInit {

  constructor(public dialogRef: MatDialogRef<AddGroupModal>,
              @Inject(MAT_DIALOG_DATA) public data: {name}) {
  }

  ngOnInit() {
  }

  back(): void {
    this.dialogRef.close();
  }

}
