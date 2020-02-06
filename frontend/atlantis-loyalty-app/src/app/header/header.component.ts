import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {

  isAuthenticated = true;
  authUserSub: Subscription;
  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.authUserSub = this.authService.authenticated.subscribe(authUser => {
      this.isAuthenticated = !authUser ? false: true;
      this.isAuthenticated = !!authUser;
    });
  }

  onLogout() {
    this.authService.logout();
    this.isAuthenticated = false;
  }

  ngOnDestroy(){
    this.authUserSub.unsubscribe();
  }

  onPurchasePoints(){
    console.log("onPurchasePoints() called")
  }
}
