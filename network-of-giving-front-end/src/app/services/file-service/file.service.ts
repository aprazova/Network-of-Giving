import { Injectable } from '@angular/core';
import { Observable } from 'rxjs'
import { HttpClient} from '@angular/common/http'
import { ImagePayload } from '../../add-charity/image';
import { map } from 'rxjs/operators';
import { CharityPayload } from '../../add-charity/charity-payload';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  url: string = "http://localhost:8090/api/images";
  constructor(private http: HttpClient) { }
    
  getImages(): Observable<Array<ImagePayload>>{
      return this.http.get<Array<ImagePayload>>(this.url);
    }

  getImage(imageName: String): Observable<ImagePayload>{
      return this.http.get<ImagePayload>(this.url + "/" + imageName.split(".")[0]);
  }
  
}
