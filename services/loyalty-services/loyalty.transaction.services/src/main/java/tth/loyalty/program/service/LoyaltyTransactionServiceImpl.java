package tth.loyalty.program.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import com.netflix.infix.lang.infix.antlr.EventFilterParser.boolean_expr_return;

import tth.loyalty.program.api.LoyaltyTransactionController;
import tth.loyalty.program.data.LoyaltyCustomerRepo;
import tth.loyalty.program.data.LoyaltyTransactionRepo;
import tth.loyalty.program.exception.InvalidRequestException;
import tth.loyalty.program.model.ConfirmPaymentTransaction;
import tth.loyalty.program.model.LoyaltyCustomer;
import tth.loyalty.program.model.LoyaltyTransaction;
import tth.loyalty.program.model.PaymentGatewayServiceProxy;
import tth.loyalty.program.model.PaymentTransaction;
import tth.loyalty.program.model.TransactionStatus;
import tth.loyalty.program.model.TransactionType;

public class LoyaltyTransactionServiceImpl implements LoyaltyTransactionService {

	private static final String INSUFFICIENT_AMOUNT = "INSUFFICIENT_AMOUNT";
	private static final String INSUFFICIENT_POINTS = "INSUFFICIENT_POINTS";
	private static final String INSUFFICIENT_BALANCE = "INSUFFICIENT BALANCE";
	private static int CONVERSION_MULTIPLE = 100;
	private static int MIN_FUNDS = 100;

	private static final Logger log = LoggerFactory.getLogger(LoyaltyTransactionController.class);

	@Autowired
	private PaymentGatewayServiceProxy paymentProxy;

	@Autowired
	private LoyaltyTransactionRepo transactionRepository;
	@Autowired
	private LoyaltyCustomerRepo customerRepository;

	@Override
	public List<LoyaltyTransaction> getLoyaltyTransactions(LoyaltyCustomer customer) {

		if (customer == null) {
			log.info("customer is null");
			throw new NoResultException("No customer found with empty ID ");
		}

		Long customerId = customer.getId();
		if (customerId == null) {
			log.info("customerId is null");
			throw new NoResultException("No customer found with empty ID ");
		}
		log.info("get customer from db with id: " + customerId);
		return transactionRepository.findAllByCustomerId(customerId);
		// return
		// (List<LoyaltyTransaction>)customerRepository.getOne(customerId).getTransactions();

	}


	/**
	 * update the transaction details for requested Customer
	 * @param customer
	 * @param request containing the transaction details
	 * @return customer updated as per transaction
	 */
	@Override
	public LoyaltyCustomer updateTransaction(LoyaltyCustomer customer, LoyaltyTransaction request,
			TransactionType transactionType) {
		// Based on the type of transaction process the same
		if (transactionType != null) {
			switch (transactionType) {
			case PURCHASE:
				purchasePoints(customer, request);
				break;
			case ACQUIRE:
				acquirePoints(customer, request);
				break;
			case REDEEM:
				redeemPoints(customer, request);
			}
		}
		return customer;
	}

	/**
		 * Redeem is invoked from customer uses points to purchase something
		 * So, points are converted to cash
		 * Based on the total points existing, check if points being redeemed is <= balance
		 * if yes, reduce points and confirm
		 * if no, throw error insufficient points
		 * update the transaction details for requested Customer
	 * @param customer
	 * @param request containing the transaction details
	 * @return customer updated as per transaction
	 */
	private void redeemPoints(LoyaltyCustomer customer, LoyaltyTransaction transaction) {
		log.info("redeemPoints fetch details from redeemPoints request");
		if (transaction == null | customer == null) {
			log.info("redeemPoints request or customer is null");
			throw new NoResultException("Customer or request details not provided");
		}

		Long points = transaction.getLoyaltyPoints();
		Long balance = customer.getBalance();
		BigDecimal amount;
		boolean converted = false;
		try {
			amount = getAmountForPoints(points);
			transaction.setTransactionAmount(amount);
			if (balance >= points) {
				log.info("redeemPoints succesfully converted, go ahead and create transaction record in DB as SUCCESS");
				converted = true;
			} else {
				log.info("redeemPoints less balance , go ahead and create transaction record in DB as FAILED");
				transaction.setStatus(TransactionStatus.FAILED);
				transaction.setFailureReason(INSUFFICIENT_BALANCE);
				saveTransaction(transaction);
				throw new NoResultException(
						"Insufficient balance to redeem! Please purchase more points if required. ");
			}
		} catch (InvalidRequestException e) {
			transaction.setStatus(TransactionStatus.FAILED);
			transaction.setFailureReason(INSUFFICIENT_POINTS);
		} finally {
			saveTransaction(transaction);
			if (converted) {
				customer.setBalance(balance - points);
				saveCustomer(customer);
			}
		}

		// update the customer and transaction info as required to send in the
		// response
		Set<LoyaltyTransaction> ls = new HashSet<LoyaltyTransaction>();
		transaction.setLoyaltyCustomer(null);
		ls.add(transaction);
		customer.setTransactions(ls);
	}


	/**
		 *  Acquire is invoked from customer uses purchase something and it
		 *  results in points. So, cash is converted to points
		 *  Based on the total cash spent,
		 *  if min cash is spend, get points for cash and, increment the balance
		 *  if no, throw error insufficient funds to acquire	
	 * @param customer
	 * @param request containing the transaction details
	 * @return customer updated as per transaction
	 */
	private void acquirePoints(LoyaltyCustomer customer, LoyaltyTransaction transaction) {
		log.info("acquirePoints fetch details from request");
		if (transaction == null | customer == null) {
			log.info("acquirePoints request or customer is null");
			throw new NoResultException("Customer or request details not provided");
		}

		Long balance = customer.getBalance();
		BigDecimal amount = transaction.getTransactionAmount();
		Long points;
		boolean converted = false;
		try {
			points = this.getPointsForAmount(amount);
			transaction.setLoyaltyPoints(points);
			log.info("acquirePoints succesfully converted, go ahead and create transaction record in DB as SUCCESS");
			if (balance == null)
				customer.setBalance(points);
			else
				customer.setBalance(balance + points);
			converted = true;
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			log.info("acquirePoints invalid amount , go ahead and create transaction record in DB as FAILED");
			transaction.setStatus(TransactionStatus.FAILED);
			transaction.setFailureReason(INSUFFICIENT_AMOUNT);			
		} finally {			
			transaction.setLoyaltyCustomer(customer);
			saveTransaction(transaction);
			//if (converted)
			//	saveCustomer(customer);
		}

		// update the customer and transaction info as required to send in the
		// response
		Set<LoyaltyTransaction> ls = new HashSet<LoyaltyTransaction>();
		transaction.setLoyaltyCustomer(null);
		ls.add(transaction);
		customer.setTransactions(ls);
	}

	private void purchasePoints(LoyaltyCustomer customer, LoyaltyTransaction transaction) {
		log.info("fetch details from request");
		if (transaction == null | customer == null) {
			log.info("request or customer is null");
			throw new NoResultException("Customer or request details not provided");
		}
		PaymentTransaction paymentTransaction = new PaymentTransaction();
		paymentTransaction.setAmount(transaction.getTransactionAmount());
		paymentTransaction.setFrom(transaction.getSource());
		paymentTransaction.setTo("ATLANTIS");
		paymentTransaction.setRemarks(transaction.getTransactionRemarks());
		if (transaction.getTransactionDate() != null) {
			transaction.getTransactionDate();
			paymentTransaction.setTransactionTime(new Date(Calendar.DATE));
		} else
			paymentTransaction.setTransactionTime(new Date());

		// perform payment
		log.info("connect to PaymentGatewayService & perform payment");
		ConfirmPaymentTransaction paymentConfirmation = paymentProxy.createTransaction(paymentTransaction);

		if (paymentConfirmation != null) {
			// if success, create transaction record in DB
			log.info("if success, create transaction record in DB");

			transaction.setStatus(TransactionStatus.SUCCESS);
			Long balance = customer.getBalance();
			customer.setBalance(balance + transaction.getLoyaltyPoints());
			transaction.setLoyaltyCustomer(customer);
			saveTransaction(transaction);
				//saveCustomer(customer);
		} else {
			// else log error and throw specific exception
			log.info("else log error, save failed transaction and throw specific exception");
			transaction.setStatus(TransactionStatus.FAILED);
			saveTransaction(transaction);
			throw new AsyncRequestTimeoutException();
		}
	}

	@Override
	public BigDecimal getAmountForPoints(Long points) throws InvalidRequestException {
		// TODO - move the conversion multiple to spring cloud config
		log.info("getAmountForPoints -  points: " + points);
		BigDecimal amount;
		if (points != null)
			amount = new BigDecimal(points * CONVERSION_MULTIPLE);
		else
			throw new InvalidRequestException("Points is not set or invalid.");

		return amount;
	}

	@Override
	public Long getPointsForAmount(BigDecimal amount) throws InvalidRequestException {
		// TODO - move the conversion multiple to spring cloud config
		log.info("getPointsForAmount - amount:" + amount);
		BigDecimal divisor = new BigDecimal(CONVERSION_MULTIPLE);
		BigDecimal minFunds = new BigDecimal(MIN_FUNDS);
		BigDecimal points = null;
		log.info("getPointsForAmount -  divisor: " + divisor + " minFunds: " + minFunds);
		if (amount != null && amount.compareTo(minFunds) >= 0) {
			points = amount.divide(divisor);
		} else {
			throw new InvalidRequestException("Amount is not set or invalid.");
		}
		return points.longValue();
	}

	@Override
	public void saveTransaction(LoyaltyTransaction loyaltyTransaction) {
		log.info("saving the transaction ");
		transactionRepository.save(loyaltyTransaction);
	}

	public void saveCustomer(LoyaltyCustomer customer) {
		log.info("saving the customer ");
		customerRepository.save(customer);
	}

}
