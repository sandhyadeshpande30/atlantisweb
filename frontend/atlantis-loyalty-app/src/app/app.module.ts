import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
//import { CookieService } from 'ngx-cookie-service';
//import { TRACE_MODULE_CONFIGURATION, ZipkinModule } from '@angular-tracing/zipkin';


import { AppComponent } from './app.component';
import { AuthComponent } from './auth/auth.component';
import { HeaderComponent } from './header/header.component';
import { LoyaltyComponent } from './loyalty/loyalty.component';
import { PointsEditComponent } from './loyalty/points-edit/points-edit.component';
import { PointsPurchaseComponent } from './loyalty/points-purchase/points-purchase.component';
import { PointsViewComponent } from './loyalty/points-view/points-view.component';
import { AppRoutingModule } from './app-routing-module';
/*
export function getZipkinConfig() {
  return {
    traceProvider: {
      http: {
       remoteServiceMapping: {
          all: /.
        }
      },
      logToConsole: true
    }
  };
}*/

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    HeaderComponent,
    LoyaltyComponent,
    PointsEditComponent,
    PointsPurchaseComponent,
    PointsViewComponent
   ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule//, 
    //ZipkinModule.forRoot()
  ],
  //providers: [{ provide: TRACE_MODULE_CONFIGURATION, useFactory: getZipkinConfig }],
  //CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
