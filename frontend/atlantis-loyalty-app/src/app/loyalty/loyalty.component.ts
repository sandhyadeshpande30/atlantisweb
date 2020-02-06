import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';

import { AuthService } from '../auth/auth.service';
import { LoyaltyUser } from '../shared/loyalty-user.model';
import { LoyaltyService } from './loyalty.service';


@Component({
  selector: 'app-loyalty',
  templateUrl: './loyalty.component.html',
  styleUrls: ['./loyalty.component.css']
})
export class LoyaltyComponent implements OnInit, OnDestroy {

  authUserSub: Subscription;
  loyaltyUser : LoyaltyUser;
  loyaltyUserSub: Subscription;
  token: string;
  //userName : String;
  isViewTransactions = false;

  constructor(private authService: AuthService, private router: Router,
    private route: ActivatedRoute, private loyaltyService: LoyaltyService) { }

  ngOnInit() {
    this.authUserSub = this.authService.authenticated.subscribe(resData => {
      if(resData) {
        this.token = resData.token;
        console.log(this.token);
      }
    });
    this.loyaltyUserSub =this.loyaltyService.getCustomerDetails(this.token).subscribe(resData => {
      //this.loyaltyService.setLoyaltyUser(resData);
      this.loyaltyUser = new LoyaltyUser(resData.firstName, resData.lastName, 
        resData.balance, resData.transactions);
  });
    
    console.log(this.loyaltyUser);
  }

  onViewTransactions(){
    
    if(!this.isViewTransactions) {
      this.router.navigate(["view"], {relativeTo: this.route});
    } else {
      this.router.navigate(["atlantis-loyalty"]);
    }
    this.isViewTransactions = !this.isViewTransactions;
  }

  ngOnDestroy(){
    this.authUserSub.unsubscribe();
  }

}
