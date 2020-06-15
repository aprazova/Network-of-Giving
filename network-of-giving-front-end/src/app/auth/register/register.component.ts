import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { RegisterPayload } from '../register-payload';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  registerPayload: RegisterPayload;
  invalidUsername: boolean;
  invalidPassword: boolean;
  invalidAge: boolean;
  invalidName: boolean;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) { 
    this.registerForm = this.formBuilder.group(
      {
        username: '',
        name: '',
        password: '',
        age: null,
        gender: '',
        location: ''
      });

    this.registerPayload = {
      username: '',
      name: '',
      password: '',
      age: null,
      gender: '',
      location: ''
    }

    this.invalidUsername = false;
    this.invalidPassword = false;
    this.invalidAge = false;
    this.invalidName = false;
  }

  ngOnInit(): void {
    if(this.authService.isAuthenticated()){
      this.router.navigateByUrl('/home');
    }
  }

  onSubmit(){

    document.getElementById('invalid-username').innerText="";

    this.invalidUsername = false;
    this.invalidPassword = false;
    this.invalidAge = false;
    this.invalidName = false;

    if(!this.validateForm(this.registerForm)){
      return;
    }

    this.registerPayload.username = this.registerForm.get('username').value;
    this.registerPayload.name = this.registerForm.get('name').value;
    this.registerPayload.password = this.registerForm.get('password').value;
    this.registerPayload.age = this.registerForm.get('age').value;
    this.registerPayload.gender = this.registerForm.get('gender')?.value;
    this.registerPayload.location = this.registerForm.get('location')?.value;
  
    this.authService.register(this.registerPayload).subscribe(data => {
      this.router.navigateByUrl('/login');
    }, error => { 
      if(error.status === 400){
        document.getElementById('invalid-username').innerText="User with this username already exist.";
        this.invalidUsername = true;
      } else {
        document.getElementById('invalid-username').innerText="There is a problem with registration. Pleasе, try again leter.";
      }
    })
  };

  validateForm(form: FormGroup){
    let result: boolean = true;

    let username: string = form.get('username').value
    if( !username || !username.trim()){
      result = false;
      this.invalidUsername = true;
    }

    let name: string = form.get('name').value
    if( !name || !name.trim()){
      result = false;
      this.invalidName = true;
    }

    let password: string = form.get('password').value;
    if( !password || !password.trim()){
      result = false;
      this.invalidPassword = true;
    }
    
    let age: number = form.get('age').value;
    if(!age || age < 1 || age > 100){
      result = false;
      this.invalidAge = true;
    }

    return result;
  }
}
