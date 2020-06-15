import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CharityPayload } from 'src/app/add-charity/charity-payload';
import { TransactionPayload } from 'src/app/models/transaction-payload';
import { AuthService } from 'src/app/auth/auth.service';
import { UserEditComponent } from 'src/app/user-edit/user-edit.component';
import { GetUserService } from 'src/app/services/user-service/get-user.service';

export interface DonationData {
  donation: number;
  charity: CharityPayload;
}

@Component({
  selector: 'app-donate-dialog',
  templateUrl: './donate-dialog.component.html',
  styleUrls: ['./donate-dialog.component.css']
})
export class DonateDialogComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<DonateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DonationData){

    }

  ngOnInit(): void {
  }


  save(): void{
    document.getElementById('error').innerText="";

    if(this.validateDonation(this.data.donation, this.data.charity)){
      this.dialogRef.close(this.data.donation);
    } else {
      document.getElementById('error').innerText="Please, enter a valid amount.";
    }
  }

  validateDonation(donationInput: number, charity:CharityPayload): boolean{
    const regExp = /^\d+(\.\d{1,2})?$/;
    if(donationInput && regExp.test(donationInput.toString())){
      if(charity.requiredBudget < (charity.currentBudget + donationInput)){
        return false;
      }
      return true;
    } 
    return false;
  }
}
