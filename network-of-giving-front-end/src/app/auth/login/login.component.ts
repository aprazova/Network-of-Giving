import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { LoginPayload } from '../login-payload';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginPayload: LoginPayload;
  isValid: boolean;
  constructor(private authService: AuthService, private router: Router ) { 
    this.loginForm = new FormGroup({
      username: new FormControl(),
      password: new FormControl()
      });

    this.loginPayload = {
      username: '',
      password: ''
    };

    this.isValid = true;
  }

  ngOnInit(): void {
    if(this.authService.isAuthenticated()){
      this.router.navigateByUrl('/home');
    }
  }

  onSubmit(){
    this.loginPayload.username = this.loginForm.get('username').value;
    this.loginPayload.password = this.loginForm.get('password').value;

    this.authService.login(this.loginPayload).subscribe(
      data => {
        this.router.navigateByUrl('/home');
      }, error => {
        this.isValid = false;
      }
    )
  }

}
