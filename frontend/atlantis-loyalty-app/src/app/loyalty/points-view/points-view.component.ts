import { Component, OnInit } from '@angular/core';
import { LoyaltyService } from '../loyalty.service';
import { LoyaltyTransaction } from 'src/app/shared/loyalty-transaction.model';
//import { LocalTracer, ZipkinTraceRoot } from '@angular-tracing/zipkin';

@Component({
  selector: 'app-points-view',
  templateUrl: './points-view.component.html',
  styleUrls: ['./points-view.component.css']
})
export class PointsViewComponent implements OnInit {

 // private tracer: LocalTracer;

  transactions: LoyaltyTransaction[];
  constructor(private loyaltyService: LoyaltyService){//}, traceRoot: ZipkinTraceRoot) { 

   // this.tracer = traceRoot.localTracer();
  }

  ngOnInit() {
    this.transactions = this.loyaltyService.transactions;
    try {
      //this.tracer.startSpan('expensive_history_recording_call');
      //this.tracer.setTags({ transactions: "hello user"});
      //user.id });
      //this.user.recordHistory();
    }
    finally {
      //this.tracer.endSpan();
    }
    console.log(this.transactions);
  }
}
