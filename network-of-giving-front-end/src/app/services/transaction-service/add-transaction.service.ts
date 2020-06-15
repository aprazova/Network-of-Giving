import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TransactionPayload } from '../../models/transaction-payload';
import { Observable } from 'rxjs';
import { CharityPayload } from '../../add-charity/charity-payload';

@Injectable({
  providedIn: 'root'
})
export class AddTransactionService {

  private url:string = 'http://localhost:8090/api/transaction'; 

  constructor(private httpClient: HttpClient) { }

  addTransaction(transaction: TransactionPayload): Observable<CharityPayload>{
    return this.httpClient.put<CharityPayload>(this.url, transaction);
  }
}
