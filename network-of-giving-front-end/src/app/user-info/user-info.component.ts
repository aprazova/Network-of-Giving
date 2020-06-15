import { Component, OnInit } from '@angular/core';
import { UserPayload } from '../models/user-payload';
import { AuthService } from '../auth/auth.service';
import { GetUserService } from '../services/user-service/get-user.service';
import { TransactionPayload } from '../models/transaction-payload';
import { CharityPayload } from '../add-charity/charity-payload';
import { CharityService } from '../services/charity-service/charity.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {

  user: UserPayload;
  transactions: Array<TransactionPayload>;
  userCharities: Observable<Array<CharityPayload>>;

  constructor(private authService:AuthService, 
    private userService: GetUserService,
    private charityService: CharityService) { 
  }

  ngOnInit(): void {
    this
    this.userService.getUser().subscribe(
      (data: UserPayload) => {
        this.user = data;
        this.transactions = this.user.usersTransactions;
      })

    this.userCharities = this.charityService.getUserCharities();
  }

}
