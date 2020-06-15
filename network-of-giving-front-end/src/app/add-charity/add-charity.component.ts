import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { CharityPayload } from './charity-payload';
import { CharityService } from '../services/charity-service/charity.service';
import { Router } from '@angular/router';
import { FileService} from '../services/file-service/file.service';
import { ImagePayload } from './image';
import { Observable } from 'rxjs';

const MAX_TITLE_LENGTH = 64;
const MAX_DESCRIPTION_LENGTH = 1024;
const MIN_DESCRIPTION_LENGTH = 10;

@Component({
  selector: 'app-add-charity',
  templateUrl: './add-charity.component.html',
  styleUrls: ['./add-charity.component.css']
})
export class AddCharityComponent implements OnInit {

  images:  Observable<Array<ImagePayload>>;

  addCharityForm: FormGroup;
  charityPayload: CharityPayload;
  title = new FormControl('');
  thumbnail = new FormControl('');
  description = new FormControl('');
  budget = new FormControl('');
  volunteers = new FormControl('');
  invalidTitle: boolean;
  invalidDescription: boolean;
  invalidBudgetAndParticipants: boolean;
  isSelectThumbnail: boolean;

  constructor(private addCharityService: CharityService, 
    private router: Router, private fileService: FileService) { 
    
      this.addCharityForm = new FormGroup({
      title: this.title,
      thumbnail: this.thumbnail,
      description: this.description,
      budget: this.budget,
      volunteers: this.volunteers
    });
    this.charityPayload = {
      id: null,
      owner: '',
      title: '',
      image: '',
      description: '',
      requiredBudget: null,
      currentBudget: 0,
      requiredVolunteers: null,
      currentVolunteers: 0,
      imageBody: null
    }

    this.invalidTitle = false;
    this.invalidDescription = false;
    this.invalidBudgetAndParticipants = false;
    this.isSelectThumbnail = false;
  }

  ngOnInit(): void {
    this.images = this.fileService.getImages();
  }

  createCharity(){
    this.charityPayload.title = this.addCharityForm.get('title').value;
    this.charityPayload.description = this.addCharityForm.get('description').value;
    this.charityPayload.requiredBudget = this.addCharityForm.get('budget').value;
    this.charityPayload.requiredVolunteers = this.addCharityForm.get('volunteers').value;
    
    if(this.validateCharityPayload(this.charityPayload)){
      this.addCharityService.addCharity(this.charityPayload).subscribe( data => {
        this.router.navigateByUrl('/');
      })
    }
  }

  selectImage(event){
    this.isSelectThumbnail = true;
    if(this.charityPayload.image){
      document.getElementById(this.charityPayload.image).classList.remove("chosenImage");
    }

    this.charityPayload.image = event.target.attributes.id.nodeValue;
    if(this.charityPayload.image){
      document.getElementById(this.charityPayload.image).classList.add("chosenImage");
    }  
  }

  validateCharityPayload(charityPayload: CharityPayload): boolean{
    
    this.invalidTitle = false;
    this.invalidDescription = false;
    this.invalidBudgetAndParticipants = false;
    let result:boolean = true;

    if(charityPayload.title.length < 1 || charityPayload.title.length > MAX_TITLE_LENGTH){
      this.invalidTitle = true;
      result = false;
    } 

    if(charityPayload.description.length < MIN_DESCRIPTION_LENGTH || charityPayload.description.length > MAX_DESCRIPTION_LENGTH){
      this.invalidDescription = true;
      result = false;
    }

    if(!charityPayload.requiredBudget && !charityPayload.requiredVolunteers){
      this.invalidBudgetAndParticipants = true;
      return false;
    } else {
      if(!this.validateBudget(charityPayload) || !this.validateVolunteers(charityPayload)){
        this.invalidBudgetAndParticipants = true;
        return false;
      }
    }

    if(this.validateBudget(charityPayload) )
    if(!this.isSelectThumbnail){
      result = false;
    }
    return result;
  }

  validateVolunteers(charity: CharityPayload): boolean{
    const regExp = /^\d*$/;
    if(charity.requiredVolunteers && (charity.requiredVolunteers<0 || !regExp.test(charity.requiredVolunteers.toString()))){
      return false;
    }
    return true;
  }

  validateBudget(charity: CharityPayload): boolean{
    const regExp = /^\d+(\.\d{1,2})?$/;
    if(charity.requiredBudget && (charity.requiredBudget < 0 || !regExp.test(charity.requiredBudget.toString()))){
      return false;
    }
    return true;
  }

}
