export class LoyaltyTransaction {
    constructor( 
            public type: string,
            public date: Date,
            public points: number,
            public amount:number, 
            public payFrom: string,
            public payTo: string,
            public customerId: string, 
            public remarks: string){}
}
 