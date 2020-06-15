import { Component, OnInit, OnChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CharityService } from '../services/charity-service/charity.service';
import { CharityPayload } from '../add-charity/charity-payload';
import { AuthService } from '../auth/auth.service';
import { MatDialog} from '@angular/material/dialog';
import { DonateDialogComponent } from '../dialogs/donate-dialog/donate-dialog.component';
import { TransactionPayload } from '../models/transaction-payload';
import { AddTransactionService } from '../services/transaction-service/add-transaction.service';
import { VolunteerDialogComponent } from '../dialogs/volunteer-dialog/volunteer-dialog.component';
import { DeleteDialogComponent } from '../dialogs/delete-dialog/delete-dialog.component';
import { UserPayload } from '../models/user-payload';
import { GetUserService } from '../services/user-service/get-user.service';
import { AlreadyVolunteerComponent } from '../dialogs/already-volunteer/already-volunteer.component';

@Component({
  selector: 'app-charity',
  templateUrl: './charity.component.html',
  styleUrls: ['./charity.component.css']
})
export class CharityComponent implements OnInit {

  charityId: number;
  charity: CharityPayload;
  donation: number;
  volunteer: boolean;
  transaction: TransactionPayload;

  constructor(private authService: AuthService, private router: ActivatedRoute, 
    private charityService: CharityService,
    private dialog: MatDialog,
    private transactionService: AddTransactionService,
    private navRouter: Router,
    private userService: GetUserService) {
      
      this.volunteer = false;
      this.donation = null;
      this.transaction = {
        id: null,
        charity: this.charity,
        user: new UserPayload,
        donation: null,
        isVolunteer: false,
        registeredAt: null
      }

     }

  async ngOnInit() {

    this.router.params.subscribe( params => {
      this.charityId = params['id'];
    });

    this.charityService.getCharity(this.charityId).subscribe( (data:CharityPayload) => {
      this.charity = data;
      this.transaction.charity = this.charity;
    });
  }

  isOwner(): boolean{
    return this.authService.getUsername() === this.charity.owner;
  }

  async onDonate(){  

    await this.averageDonation();
    let dialogRef = this.dialog.open(DonateDialogComponent, {
      data: {donation: this.donation, charity: this.charity}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.donation = result;
        this.volunteer = false;
      this.addTransaction();
      }
    });
  }

  onVolunteer(): void{
    let dialogRef = this.dialog.open(VolunteerDialogComponent, {

    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.volunteer = result;
        this.donation = 0;
        this.addTransaction();
      }
    }); 
  }

  onDelete():void {
    let dialogRef = this.dialog.open(DeleteDialogComponent, {});
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.charityService.deleteCharity(this.charityId).subscribe(
          () => {
            this.navRouter.navigateByUrl("/home");
          },
          error => console.log("Error.")
        );
      }
    }); 
  }

  addTransaction(): void{

    this.transaction.user.username = this.authService.getUsername();
    this.transaction.charity = this.charity;
    this.transaction.isVolunteer = this.volunteer;
    this.transaction.donation = this.donation;  

    this.transactionService.addTransaction(this.transaction).subscribe(
      (data:CharityPayload) => {
        this.charity = data
      }, (error) => {
        if(error.status === 409){
          let dialogRef = this.dialog.open(AlreadyVolunteerComponent, {
          }); 
        }
      }
    );   

  }

  async averageDonation(){
    let avrDonation: number = 0;
    let donations: number = 0;  

    await this.userService.getUserTransactions().forEach(
      (transactionArray: Array<TransactionPayload>) => {
        transactionArray.forEach((transaction: TransactionPayload) => {
        if(transaction.donation > 0){
          avrDonation += transaction.donation;
          donations += 1;
        }
      })
    }
    )
    this.donation = (donations > 0 ? avrDonation/donations : 0);
    this.donation = Math.floor(this.donation);
  }


  isAuthenticated(){
    return this.authService.isAuthenticated();
  }
}
