import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';
import { LogOutDialogComponent } from '../dialogs/log-out-dialog/log-out-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  username: String;
  constructor(public authService: AuthService, private router: Router,
    private dialog: MatDialog) { 
    this.username = authService.getUsername();
  }

  ngOnInit(): void {
  }

  logout(){

    let dialogRef = this.dialog.open(LogOutDialogComponent, {
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.authService.logout();
        this.router.navigateByUrl("/home");
      }
    });
  }

  login(){
    this.router.navigateByUrl("/login");
  }

  signup(){
    this.router.navigateByUrl("/register");  
  }

}
