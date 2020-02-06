package tth.loyalty.program.service;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import tth.loyalty.program.exception.InvalidRequestException;
import tth.loyalty.program.model.LoyaltyCustomer;
import tth.loyalty.program.model.LoyaltyTransaction;
import tth.loyalty.program.model.TransactionRequest;
import tth.loyalty.program.model.TransactionType;

/**
 * @author M1006550
 * Service interface for Loyalty Transactions
 */
@Service("LoyaltyTransactionService")
public interface LoyaltyTransactionService {
	
	/**
	 * Get all transactions for requested Customer from the local repo
	 * @param customer
	 * @return
	 */
	List<LoyaltyTransaction> getLoyaltyTransactions(LoyaltyCustomer customer);
	
	/**
	 * Add transaction details of any type for a customer to local repo
	 * @param customer
	 * @param transactionRequest 
	 * @return customer - with the latest information
	 */
	LoyaltyCustomer updateTransaction(LoyaltyCustomer customer, LoyaltyTransaction transaction, TransactionType transactionType);
	
	/**
	 * Get the transaction amount for the given points by using the conversion multiple
	 * @param loyalty points 
	 * @return amount
	 * @throws InvalidRequestException 
	 */
	public BigDecimal getAmountForPoints(Long points) throws InvalidRequestException;
	
	public void saveTransaction(LoyaltyTransaction loyaltyTransaction);

	/**
	 * Get the points for the given transaction amount using the conversion multiple as division factor
	 * @param amount 
	 * @return loyalty points
	 * @throws InvalidRequestException 
	 */
	public Long getPointsForAmount(BigDecimal amount) throws InvalidRequestException;

}
