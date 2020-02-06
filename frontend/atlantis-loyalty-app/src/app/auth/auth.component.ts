import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from './auth.service';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit, OnDestroy {
  @Input() errMessage:String;

  isLoginMode = false;
  authObs: Subscription;
  error: String;
  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
  }
  onSubmit(form : NgForm){
    if (!form.value){
      return;
    }
    console.log(form.value);
    const username = form.value.user;
    const password = form.value.password;

    if(this.isLoginMode) {
      this.authObs = this.authService.login(username, password).subscribe(resData => {
        console.log(resData);
        this.router.navigate(['/atlantis-loyalty']);
        }, error => {
        console.log(error);
        this.error = error;
      });}
      else {
      this.authObs = this.authService.register(username, password).subscribe(resData => {
        console.log(resData);
        this.router.navigate(['/atlantis-loyalty']);
      });
    }
        
  }
  onSwitchMode(){
    this.isLoginMode = !this.isLoginMode;
  }

  ngOnDestroy(){
    this.authObs.unsubscribe();
  }
}
