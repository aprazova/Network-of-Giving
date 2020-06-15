import { Component, OnInit } from '@angular/core';
import { UserPayload } from '../models/user-payload';
import { GetUserService } from '../services/user-service/get-user.service';
import { Observable } from 'rxjs';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['../auth/register/register.component.css']
})
export class UserEditComponent implements OnInit {

  user: UserPayload;
  editInfoForm: FormGroup;
  invalidUsername: boolean;
  invalidPassword: boolean;
  invalidAge: boolean;
  invalidName: boolean;

  constructor(private userService: GetUserService,
     private formBuilder: FormBuilder,
     private router: Router) {

      this.userService.getUser().subscribe(
        (data: UserPayload) => {
          this.user = data;
          this.defineFormGroup(this.user);
        });
     }

  ngOnInit(): void {

  }

  onSubmit(){
    this.user.name = this.editInfoForm.get('name')?.value;
    this.user.age = this.editInfoForm.get('age').value;
    this.user.gender = this.editInfoForm.get('gender').value; 
    this.user.location = this.editInfoForm.get('location').value;

    this.userService.editUser(this.user).subscribe(
      () => {
        this.router.navigateByUrl("/information");
      }
    );
  }

  defineFormGroup(user: UserPayload){
    this.editInfoForm = new FormGroup({
      name: new FormControl(this.user.name),
      age: new FormControl(this.user.age),
      gender: new FormControl(this.user.gender),
      location: new FormControl(this.user.location)
    }) 
   }
}
