import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserPayload } from '../../models/user-payload';
import { TOUCH_BUFFER_MS } from '@angular/cdk/a11y';
import { TransactionPayload } from 'src/app/models/transaction-payload';

@Injectable({
  providedIn: 'root'
})
export class GetUserService {


  private url:string = 'http://localhost:8090/api/user'; 

  constructor(private httpClient: HttpClient) { }

  getUser(): Observable<UserPayload>{
    return this.httpClient.get<UserPayload>(this.url);
  }

  editUser(user: UserPayload): Observable<UserPayload>{
    return this.httpClient.put<UserPayload>(this.url + "/edit", user);
  }

  getUserTransactions(): Observable<Array<TransactionPayload>>{
    return this.httpClient.get<Array<TransactionPayload>>(this.url + "/transactions");
  }
}
