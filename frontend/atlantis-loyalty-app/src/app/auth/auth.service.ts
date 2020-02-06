import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { throwError, BehaviorSubject } from 'rxjs';

import { AuthUser } from '../shared/authUser.model';
import { Router } from '@angular/router';
import { LoyaltyUser } from '../shared/loyalty-user.model';


// export interface AuthResponse {
//     userName : string;
//     password : string;
//     token : string;
// }

@Injectable({providedIn: 'root'})
export class AuthService{
    authenticated = new BehaviorSubject<AuthUser>(null);
    constructor(private http: HttpClient, private router:Router){}

    login(username: string, password: string){
        return this.http.post<AuthUser>('http://localhost:8090/loyalty/login',{
            username: username,
            password: password
        }).pipe(catchError(this.handleError),
        tap(resData => {
            this.handleAuthentication(resData.username, resData.password, resData.token);
        }));
    }

    register(username: string, password: string) {
        return this.http.post<AuthUser>('http://localhost:8090/loyalty/register', {
            username: username,
            password: password
        }).pipe(catchError(this.handleError),
        tap(resData => {
            this.handleAuthentication(resData.username, resData.password, resData.token);
        }));
    }

    autoLogin(){
       const authUserData : {
           username : string;
           password : string;
           token : string; }  = JSON.parse(localStorage.getItem('authUserData'));
          
        if(authUserData) {
            const loadUser = new AuthUser(authUserData.username,
                authUserData.password, authUserData.token);
            this.authenticated.next(loadUser);
        } else{
            this.router.navigate(['/atlantis-auth']);
        }
    }

    logout(){
        this.authenticated.next(null);
        localStorage.removeItem('authUserData');
        this.router.navigate(['/atlantis-auth']);
    }

    handleAuthentication(username: string, password: string, token: string){
        const authUser = new AuthUser(username, password, token);
        this.authenticated.next(authUser);
        localStorage.setItem('authUserData', JSON.stringify(authUser));
    }

    handleError(errRes: HttpErrorResponse){
        let errMsg = 'An Unknown error Occured!';
        //switch (errRes.error.e)
        if(!errRes || !errRes.status) {
            return throwError(errMsg);
        }
        switch(errRes.status) {
            case 404 :
                errMsg = 'User not found!';
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
}