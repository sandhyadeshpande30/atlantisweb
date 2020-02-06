import { NgModule } from '@angular/core';

import { LoyaltyComponent } from './loyalty/loyalty.component';
import { AuthComponent } from './auth/auth.component';
import { PointsPurchaseComponent } from './loyalty/points-purchase/points-purchase.component';
import { PointsViewComponent } from './loyalty/points-view/points-view.component';
import { RouterModule, Routes } from '@angular/router';

const appRoutes: Routes = [
    {path :'', redirectTo: '/atlantis-loyalty', pathMatch: 'full'},
    {path :'atlantis-auth', component:AuthComponent},    
    {path :'atlantis-purchase', component:PointsPurchaseComponent},
    {path :'atlantis-loyalty', component:LoyaltyComponent,
        children : [ 
            {path : 'view', component: PointsViewComponent}
        ]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})

export class AppRoutingModule {

}