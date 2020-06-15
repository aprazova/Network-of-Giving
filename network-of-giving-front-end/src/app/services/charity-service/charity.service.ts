import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CharityPayload } from '../../add-charity/charity-payload';
import { Observable, Observer } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CharityService {
  private url:string = 'http://localhost:8090/api/charity'; 

  constructor(private httpClient: HttpClient) { }

  addCharity(charityPayload: CharityPayload){
    return this.httpClient.post(this.url, charityPayload);
  }

  getAllCharities(): Observable<Array<CharityPayload>>{
    return this.httpClient.get<Array<CharityPayload>>(this.url + '/all');
  }

  getCharity(charityId: number): Observable<CharityPayload>{
    return this.httpClient.get<CharityPayload>(this.url + '/get/' + charityId);
  }

  deleteCharity(charityId: number): Observable<void> {
    return this.httpClient.delete<void>(this.url + '/delete/' + charityId);
  }

  getUserCharities(): Observable<Array<CharityPayload>>{
    return this.httpClient.get<Array<CharityPayload>>(this.url+"/own");
  }

  editCharity(charity: CharityPayload): Observable<CharityPayload>{
    return this.httpClient.put<CharityPayload>(this.url + "/edit/" + charity.id, charity);
  }

}


