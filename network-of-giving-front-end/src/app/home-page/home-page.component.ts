import { Component, OnInit } from '@angular/core';
import { CharityService } from '../services/charity-service/charity.service';
import { CharityPayload } from '../add-charity/charity-payload';
import { Observable } from 'rxjs';
import { ImagePayload } from '../add-charity/image';
import { FileService} from '../services/file-service/file.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  charities: Observable<Array<CharityPayload>>;
  searchTerm: string;
  constructor(private charityService: CharityService, private  fileService: FileService) { }

  ngOnInit(): void {
    this.charities = this.charityService.getAllCharities();

  }

}
