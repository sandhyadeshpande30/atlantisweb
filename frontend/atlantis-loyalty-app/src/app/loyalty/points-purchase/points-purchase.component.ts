import { Component, OnInit, OnDestroy, Input } from '@angular/core';
//import { LocalTracer, ZipkinTraceRoot } from '@angular-tracing/zipkin';
import { FormsModule } from '@angular/forms';
//import { ActivatedRoute} from '@angular/router';
import { Router } from '@angular/router';
import { LoyaltyTransaction } from '../../shared/loyalty-transaction.model';
import { LoyaltyService} from '../loyalty.service';
import { NgForm } from '@angular/forms'; 

import { Subscription } from 'rxjs';
import { getLocaleDateFormat } from '@angular/common';





@Component({
  selector: 'app-points-purchase', 
  providers: [LoyaltyService],  
  templateUrl: './points-purchase.component.html',
  styleUrls: ['./points-purchase.component.css']
})

export class PointsPurchaseComponent implements OnInit {

  @Input() message:String;
  points: number;  
  loyaltySubscription: Subscription;
  loyaltyTransaction : LoyaltyTransaction;
  @Input() calculatedAmount : number;

 // private tracer: LocalTracer;

  constructor(
    private loyaltyService: LoyaltyService, 
    private router: Router){//}, traceRoot: ZipkinTraceRoot) { 
    //this.tracer = traceRoot.localTracer();    
  }

  ngOnInit() { 
   } 
     
  ngOnDestroy(){ 
    this.message = "";
    this.loyaltySubscription.unsubscribe();
  }

  getAmountForPoints(points: string) { 
    console.log("points entered: " + points); 
    try {
     
    this.loyaltySubscription = this.loyaltyService.getAmountForPoints(points).
      subscribe(resData => {
        this.calculatedAmount = resData;
        this.message = "Amount has been fetched! Please fill the pay from and to details and proceed with payment.";
        console.log("calculated amount is: " + resData); 
    });    
   // this.tracer.startSpan('getAmountForPoints');
    
  }
   // this.tracer.setTags({ "points": points});
    //this.user.recordHistory();
  finally {
  //  this.tracer.endSpan();
  }
  }
  
  purchase(form: NgForm, event) { 
    if (!form.value){
      this.message = "Please fill in the details to proceed!";
      return;
    }
    console.log("Form values");
    console.log(form.value); 
    console.log("Calculated amount: ");
    console.log(this.calculatedAmount); 
    this.loyaltyTransaction = new LoyaltyTransaction("PURCHASE",new Date(),
                                form.value.points,
                                this.calculatedAmount,
                                form.value.payFrom,
                                "ATLANTIS",                                
                                "1", "sending from the app");
    console.log("request info: ");
    console.log(this.loyaltyTransaction);
    
    this.loyaltySubscription = this.loyaltyService
    .purchase(this.loyaltyTransaction).subscribe(resData => {
      this.message = "Payment transaction done!" + resData;
      console.log(resData); }); 
  }
 
}