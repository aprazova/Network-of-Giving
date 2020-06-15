import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CharityPayload } from '../add-charity/charity-payload';
import { CharityService } from '../services/charity-service/charity.service';
import { FormGroup, FormControl } from '@angular/forms';
import { FileService } from '../services/file-service/file.service';
import { ImagePayload } from '../add-charity/image';
import { Observable } from 'rxjs';
import { AddCharityComponent } from '../add-charity/add-charity.component';

const MAX_TITLE_LENGTH = 64;
const MAX_DESCRIPTION_LENGTH = 1024;
const MIN_DESCRIPTION_LENGTH = 10;

@Component({
  selector: 'app-charity-edit',
  templateUrl: './charity-edit.component.html',
  styleUrls: ['../add-charity/add-charity.component.css']
})
export class CharityEditComponent implements OnInit {

  images:  Observable<Array<ImagePayload>>;
  editCharityForm: FormGroup;
  charityId: number;
  charity: CharityPayload;
  invalidTitle: boolean;
  invalidDescription: boolean;
  invalidBudgetAndParticipants: boolean;
  isSelectThumbnail: boolean;
  
  constructor(private router: ActivatedRoute,
    private charityService: CharityService,
    private fileService: FileService,
    private navrRouter: Router) {

      this.invalidTitle = false;
      this.invalidDescription = false;
      this.invalidBudgetAndParticipants = false;
      this.isSelectThumbnail = true;
     }

  ngOnInit(): void {
    this.router.params.subscribe( params => {
      this.charityId = params['id'];
    });
    this.charityService.getCharity(this.charityId).subscribe(
      (data: CharityPayload) => {
        this.charity = data;
        this.defineEditCharityForm(this.charity);
      }
    );
    this.images = this.fileService.getImages();
  }

  selectImage(event): void{
    this.isSelectThumbnail = true;
    if(this.charity.image){
      document.getElementById(this.charity.image).classList.remove("chosenImage");
    }

    this.charity.image = event.target.attributes.id.nodeValue;
    if(this.charity.image){
      document.getElementById(this.charity.image).classList.add("chosenImage");
    }  
  }

  defineEditCharityForm(charity: CharityPayload): void{
    this.editCharityForm = new FormGroup({
      title: new FormControl(charity.title),
      image: new FormControl(charity.image),
      description: new FormControl(charity.description),
      budget: new FormControl(charity.requiredBudget),
      volunteers: new FormControl(charity.requiredVolunteers)
    });
  }
  editCharity(): void{

    let updatedBudget: number = this.editCharityForm.get('budget').value;
    let updatedParticipant: number = this.editCharityForm.get('volunteers').value;
    if(this.charity.currentBudget > updatedBudget){
      this.invalidBudgetAndParticipants = true;
      return;
    }

    if(this.charity.currentVolunteers > updatedParticipant){
      this.invalidBudgetAndParticipants = true;
      return;
    }

    this.charity.title = this.editCharityForm.get('title').value;
    this.charity.description = this.editCharityForm.get('description').value;
    this.charity.requiredBudget = this.editCharityForm.get('budget').value;
    this.charity.requiredVolunteers = this.editCharityForm.get('volunteers').value;
    
    if(this.validateCharityPayload(this.charity)){
      this.charityService.editCharity(this.charity).subscribe( data => {
        this.navrRouter.navigateByUrl('/charity/' + this.charityId);
      })
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

    if((!charityPayload.requiredBudget && !charityPayload.requiredVolunteers) || charityPayload.requiredBudget<0 || charityPayload.requiredVolunteers<0){
      this.invalidBudgetAndParticipants = true;
      return false;
    }

    if(!this.isSelectThumbnail){
      result = false;
    }
    return result;
  }

}
