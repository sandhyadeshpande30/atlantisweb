import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { map, catchError, tap } from 'rxjs/operators';
import { throwError, BehaviorSubject } from 'rxjs';

import { LoyaltyUser } from '../shared/loyalty-user.model';

import { AuthUser } from '../shared/authUser.model'
import { LoyaltyTransaction } from '../shared/loyalty-transaction.model'
//import { LoyaltyTransactionModel } from '../shared/loyalty-transaction.model';
import { Router } from '@angular/router'; 
import { analyzeAndValidateNgModules } from '@angular/compiler';
//import { CookieService } from 'ngx-cookie-service';



@Injectable({providedIn: 'root'})
export class LoyaltyService {
    authenticated = new BehaviorSubject<AuthUser>(null);
    constructor(private httpClient: HttpClient, private router:Router
        //, private cookieService: CookieService
        ){}

    todayDate:Date;
    calculatedAmount:number;
    loyaltyUser : LoyaltyUser;

    transactions = [new LoyaltyTransaction('AQUIRE', 
            new Date(), 25,100, 'spd','ATLANTIS', "1", 'Acquired Points'),
            new LoyaltyTransaction('REDEEM', 
            new Date(), 30, 90, 'ATLANTIS','spd', "1", 'Points Redeemed') ];

    
    getCustomerDetails(userToken: string){
        
       return this.httpClient.get<LoyaltyUser>("http://localhost:8083/loyalty/customer",
        {
            headers :  new HttpHeaders().set('secure-token', userToken),
            withCredentials : true
        });
    }
    
    getAmountForPoints(points: string){         
        endPointUrl: String;

        let endPointUrl = 'http://localhost:8083/loyalty/amountForPoints/' + points;
        
        const headerList = new HttpHeaders().set('Content-Type', 'application/json');        
        const optionList = { headers: headerList};  
        return this.httpClient.get<any>(endPointUrl, optionList)
        .pipe(catchError(this.handleError),
        tap(resData => {
            console.log(resData);
        }));  
    }

    purchase(transaction: LoyaltyTransaction){
        return this.httpClient.post<any>('http://localhost:8083/loyalty/customer/dpradhan/purchase',{
                
              "transaction": { 
                "transactionAmount":transaction.amount,
                "loyaltyPoints":transaction.points,                
                "transactionRemarks":"testing via angular app ui",
                "source":transaction.payFrom,
                "loyaltyCustomer":{
                        "id":1,
                        "userID":"dpradhan"}
                }              
        }).pipe(catchError(this.handleError),
        tap(resData => {
            console.log(resData);
        }));
    } 

    handleError(errRes: HttpErrorResponse){
        let errMsg = 'An Unknown error Occured!' + errRes;
         if(!errRes || !errRes.status) {
            return throwError(errMsg);
        }
        switch(errRes.status) {
            case 404 :
                errMsg = 'User not found or service unavailable!';
                break;
            case 401 :
                errMsg = 'Invalid credentials!';
                break;
            case 500 :
                errMsg = 'Internal Server Error!';
                break;
        }
        console.log(errMsg);
        return throwError(errMsg);
    }

    setLoyaltyUser(loyaltyUser: LoyaltyUser){
        this.loyaltyUser = loyaltyUser;
    }
    getLoyaltyUser(){
        return this.loyaltyUser;
    }
}