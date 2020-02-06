package tth.loyalty.program.model;

import javax.validation.constraints.NotNull;

public class TransactionRequest {
	
	@NotNull
	private LoyaltyTransaction transaction; 
	
	public TransactionRequest() { 
	}
	
	
	public TransactionRequest(@NotNull LoyaltyTransaction transaction){ 
		super();
		this.transaction = transaction; 
	} 
	public LoyaltyTransaction getTransaction() {
		return transaction;
	}
	public void setTransaction(LoyaltyTransaction transaction) {
		this.transaction = transaction;
	}

}
