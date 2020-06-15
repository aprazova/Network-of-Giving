import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-volunteer-dialog',
  templateUrl: './volunteer-dialog.component.html',
  styleUrls: ['./volunteer-dialog.component.css']
})
export class VolunteerDialogComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<VolunteerDialogComponent>) { }

  ngOnInit(): void {
  }
  becomeVolunteer():void{
    this.dialogRef.close(true);
  }
}
