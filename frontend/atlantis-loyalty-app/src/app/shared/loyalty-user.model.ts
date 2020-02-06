import { AuthUser } from './authUser.model';
import { LoyaltyTransaction } from './loyalty-transaction.model';

export class LoyaltyUser {
    constructor(
            //public authUser: AuthUser, 
            public firstName: string,
            public lastName:string, 
            public balance: number,
            public transactions: LoyaltyTransaction){}
}