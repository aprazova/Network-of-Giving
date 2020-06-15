import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-log-out-dialog',
  templateUrl: './log-out-dialog.component.html',
  styleUrls: ['./log-out-dialog.component.css']
})
export class LogOutDialogComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<LogOutDialogComponent>) { }

  ngOnInit(): void {
  }

  submitRequest(){
    this.dialogRef.close(true);
  }

}
