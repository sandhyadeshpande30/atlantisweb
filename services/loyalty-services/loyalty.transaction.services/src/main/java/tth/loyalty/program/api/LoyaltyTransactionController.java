package tth.loyalty.program.api;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import tth.loyalty.program.exception.InvalidRequestException;
import tth.loyalty.program.model.LoyaltyCustomer;
import tth.loyalty.program.model.LoyaltyTransaction;
import tth.loyalty.program.model.TransactionRequest;
import tth.loyalty.program.model.TransactionStatus;
import tth.loyalty.program.model.TransactionType;
import tth.loyalty.program.service.LoyaltyCustomerService;
import tth.loyalty.program.service.LoyaltyTransactionService;

/**
 * @author M1006550
 * Controller for Loyalty Transaction
 */
@RestController
public class LoyaltyTransactionController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(LoyaltyTransactionController.class);

	
	@Autowired
	private LoyaltyCustomerService customerService;
	
	@Autowired
	private LoyaltyTransactionService transactionService;
	
	/**
	 * Fetch details for requested Customer "/loyalty/customer/{userId}",
	 * @param customer
	 * @return
	 */
	@RequestMapping(
			value = "/loyalty/customer/{userId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod="fallbackGetCustomerDetails")
	@CrossOrigin(allowedHeaders="*")	
	public ResponseEntity<LoyaltyCustomer> getCustomerDetails(@PathVariable("userId") String user){
		
		log.info("user: " + user);
		LoyaltyCustomer customer = customerService.getCustomerDetails(user);
		log.info("get customer from db done");
		if(customer == null) {
			log.info("get csutomer from db returned null");
			throw new NoResultException("No customer found with ID : " + user);
		}
		log.info("customer retreieved:",customer.toString() );
		return new ResponseEntity<LoyaltyCustomer>(customer, HttpStatus.OK);
		
	}
	
	/**
	 * Fallback method for the fetch details for requested Customer
	 * @param customer id
	 * @return
	 */
	public ResponseEntity<LoyaltyCustomer> fallbackGetCustomerDetails(@PathVariable("userId") String user){
		throw new NoResultException("Database is not reachable : " + user); 
	} 
	
	/**
	/**
	 * Fallback method for the fetch details for requested Customer's transactions /loyalty/customer/{userId}/transactions
	 * @param customer id
	 * @return
	 */ 
	@RequestMapping(
			value = "/loyalty/customer/{userId}/transactions",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod="fallbackGetTransactionDetails")
	@CrossOrigin(allowedHeaders="*")	
	public ResponseEntity<List<LoyaltyTransaction>> getTransactionDetails(
			@PathVariable("userId") String userId){
		
		LoyaltyCustomer customer = customerService.getCustomerDetails(userId);
		
		if(customer == null) {
			throw new NoResultException("No customer found with ID : " + userId);
		}
		
		log.info("getTransactionDetails userid : " + userId);
		List<LoyaltyTransaction> transactions = transactionService.getLoyaltyTransactions(customer);
		/*
		List<LoyaltyTransaction> transactions = new ArrayList<LoyaltyTransaction>();
		
		LoyaltyCustomer customer = new LoyaltyCustomer();
		customer.setFirstName("spd");
		customer.setId(new Long(1024)); 
		LoyaltyTransaction tr = new LoyaltyTransaction();
		tr.setId(new Long(1));
		tr.setLoyaltyCustomer(customer);
		tr.setLoyaltyPoints(new Long(10));
		tr.setTransactionAmount(new BigDecimal(1000));
		tr.setTransactionDate(Calendar.getInstance());
		tr.setType(TransactionType.PURCHASE);
		transactions.add(tr);
		 
		*/
		return new ResponseEntity<List<LoyaltyTransaction>>(transactions, HttpStatus.OK);
		//return new ResponseEntity<LoyaltyTransaction>(tr, HttpStatus.OK);
		
	}
	
	//Fallback method for the fetch details for requested Customer
	public ResponseEntity<List<LoyaltyTransaction>> fallbackGetTransactionDetails(
			@PathVariable("userId") String user){
		throw new NoResultException("Database is not reachable : " + user); 
	}
	
	/**
	 * Purchase points for requested Customer /loyalty/customer/{userId}/purchase
	 * @param customer
	 * @return
	 */
	@PostMapping(
			value = "/loyalty/customer/{userId}/purchase", 			
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod="fallbackPurchasePoints")	
	@CrossOrigin(allowedHeaders="*")	
	public ResponseEntity<LoyaltyCustomer> purchasePoints(@PathVariable("userId") String userId,
			@RequestBody TransactionRequest request){
		log.info("retrieving and validating userid : " + userId);
		//check if user is valid by retrieving the information
		LoyaltyCustomer customer = validateUserIdAndRetrieveUser(userId);
		
		log.info("validating and updating request");
		//check if transction request has all data
		updatePurchasePointsRequestMandatoryInfo(request);
		
		log.info("performing transaction");
		//perform transaction
		customer = transactionService.updateTransaction(customer, request.getTransaction(), TransactionType.PURCHASE);
		if(customer!= null){
			log.info("purchase points response recieved" + customer.toString());
		}
		//provide response		
		return new ResponseEntity<LoyaltyCustomer>(customer, HttpStatus.OK);		
	}
	
	/**
	 * Fallback for the purchase points for requested Customer
	 * @param customer
	 * @return
	 */
	public ResponseEntity<LoyaltyCustomer> fallbackPurchasePoints(@PathVariable("userId") String userId,
			@RequestBody TransactionRequest request){
		
		log.info("fallback is to set the transaction as failed in DB");
		LoyaltyTransaction transaction = request.getTransaction();
		
		log.info("set the transaction details as failed");
		transaction.setStatus(TransactionStatus.FAILED);
		transaction.setType(TransactionType.PURCHASE);
		transaction.setTransactionDate(Calendar.getInstance()); 
		request.setTransaction(transaction); 		
		
		//store the transaction as failed
		transactionService.saveTransaction(transaction);		
		log.info("transaction failed saving to DB as failed completed");
		
		LoyaltyCustomer customer = new LoyaltyCustomer();
		customer.setUserID(userId);
		ResponseEntity<LoyaltyCustomer> cust = new ResponseEntity<LoyaltyCustomer>(customer, HttpStatus.BAD_GATEWAY);
		return cust;
	}

	private LoyaltyCustomer validateUserIdAndRetrieveUser(String userId) {
		
		if( userId == null | userId.isEmpty()){
			log.info("userid not found: " + userId);
			throw new NoResultException("UserID mandatory: " + userId);
		}
		
		log.info("userid : " + userId);
		//validate if user present
		LoyaltyCustomer customer = customerService.getCustomerDetails(userId); 
		if(customer == null) {
			log.info("customer for this userid not found: " + userId);
			throw new NoResultException("No customer found with ID : " + userId);
		}
		
		log.info("customer for this userid found: " + userId);
		return customer;
	}


	/**
	 * Validate the request details and set the mandatory info if not present
	 * @param customer
	 * @return
	 */
	private void updatePurchasePointsRequestMandatoryInfo(TransactionRequest request) {
		LoyaltyTransaction transaction;
		if(request == null)  {
			log.info("input request is not set");
			throw new NoResultException("No request details provided");
		}
		transaction = request.getTransaction();
		if(transaction == null)  {
			log.info("input transaction is not set");
			throw new NoResultException("No transaction details provided");
		}
		Long points =  transaction.getLoyaltyPoints();
		BigDecimal amount = transaction.getTransactionAmount();
		if(points == null | points == 0 | amount == null | amount == BigDecimal.ZERO)  {
			log.info("input transaction details points or amount is not set");
			throw new NoResultException("No transaction details provided");
		}   
		if(transaction.getTransactionDate() == null){
			log.info("input transaction date details is not set so defaulting to current date");
			transaction.setTransactionDate(Calendar.getInstance());
		}
		if(transaction.getType() == null){
			log.info("input transaction date details is not set so defaulting to current date");
			transaction.setType(TransactionType.PURCHASE);
		}
		transaction.setStatus(TransactionStatus.SUCCESS); 
	}
	
	 /* Fetch the amount for the given points loyalty/amountForPoints/{points}
	 * @param points
	 * @return calculated amount 
	 */ 
	@GetMapping(path="loyalty/amountForPoints/{points}",produces = "application/json; charset=UTF-8")
	@Valid
	@CrossOrigin(allowedHeaders="*")	
	public BigDecimal getAmountForPoints(@Valid @PathVariable Long points) throws InvalidRequestException{		 
		
		if(points == null) {
			log.info("request is null");
			throw new NoResultException("No amount can be derived");
		}    
		log.info("Request points: " + points);	 
		return transactionService.getAmountForPoints(points);	
	}
	
	 /* Fetch the amount for the given points loyalty/pointsForAmount/{amount}
	 * @param points
	 * @return calculated amount 
	 */ 
	@GetMapping(path="loyalty/pointsForAmount/{amount}",produces = "application/json; charset=UTF-8")
	@Valid
	@CrossOrigin(allowedHeaders="*")	
	public Long getPointsForAmount(@Valid @PathVariable BigDecimal amount) throws InvalidRequestException{		 
		
		if(amount == null) {
			log.info("controller getPointsForAmount request is null");
			throw new NoResultException("No points can be derived");
		}    
		log.info("controller getPointsForAmount Request amount: " + amount);	 
		return transactionService.getPointsForAmount(amount);	
	}
	

	/**
	 * Fetch details for requested Customer /loyalty/customer/{userId}/redeem
	 * @param customer
	 * @return
	 * @throws InvalidRequestException 
	 */
	@PostMapping(
			value = "/loyalty/customer/{userId}/redeem", 			
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	//@HystrixCommand(fallbackMethod="fallbackRedeemPoints")	
	@CrossOrigin(allowedHeaders="*")	
	public ResponseEntity<LoyaltyCustomer> redeemPoints(@PathVariable("userId") String userId,
			@RequestBody TransactionRequest request) throws InvalidRequestException{
		log.info("retrieving and validating userid : " + userId);
		//check if user is valid by retrieving the information
		LoyaltyCustomer customer = validateUserIdAndRetrieveUser(userId);
		
		log.info("validating and updating request");
		//check if transction request has all data
		updateRedeemPointsRequestMandatoryInfo(request);
		
		log.info("redeem points transaction");
		//perform transaction
		customer = transactionService.updateTransaction(customer, request.getTransaction(), TransactionType.REDEEM);
		if(customer!= null){
			log.info("redeem points response recieved" + customer.toString());
		}
		//provide response		
		return new ResponseEntity<LoyaltyCustomer>(customer, HttpStatus.OK);		
	}

	private void updateRedeemPointsRequestMandatoryInfo(TransactionRequest request) throws InvalidRequestException {
		LoyaltyTransaction transaction;
		if(request == null)  {
			log.info("input request is not set");
			throw new InvalidRequestException("No request details provided");
		}
		transaction = request.getTransaction();
		if(transaction == null)  {
			log.info("input transaction is not set");
			throw new InvalidRequestException("No transaction details provided");
		}
		Long points =  transaction.getLoyaltyPoints(); 
		String source = transaction.getSource();
		if(points == null | points == 0 | source == null | source.isEmpty())  {
			log.info("input transaction details points or source not set");
			throw new InvalidRequestException("No transaction details provided - points or source not set or invalid");
		}   
		if(transaction.getTransactionDate() == null){
			log.info("input transaction date details is not set so defaulting to current date");
			transaction.setTransactionDate(Calendar.getInstance());
		}
		if(transaction.getType() == null){
			log.info("input transaction date details is not set so defaulting to current date");
			transaction.setType(TransactionType.REDEEM);
		}
		transaction.setStatus(TransactionStatus.SUCCESS); 
	}
	


	/**
	 * Fetch details for requested Customer /loyalty/customer/{userId}/acquire
	 * @param customer
	 * @return
	 */
	@PostMapping(
			value = "/loyalty/customer/{userId}/acquire", 			
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	//@HystrixCommand(fallbackMethod="fallbackRedeemPoints")	
	@CrossOrigin(allowedHeaders="*")	
	public ResponseEntity<LoyaltyCustomer> acquirePoints(@PathVariable("userId") String userId,
			@RequestBody TransactionRequest request){
		log.info("retrieving and validating userid : " + userId);
		//check if user is valid by retrieving the information
		LoyaltyCustomer customer = validateUserIdAndRetrieveUser(userId);
		
		log.info("validating and updating acquire request");
		//check if transction request has all data
		updateAcquirePointsRequestMandatoryInfo(request);
		
		log.info("acquire points transaction");
		//perform transaction
		customer = transactionService.updateTransaction(customer, request.getTransaction(), TransactionType.ACQUIRE);
		if(customer!= null){
			log.info("acquire points response recieved" + customer.toString());
		}
		//provide response		
		return new ResponseEntity<LoyaltyCustomer>(customer, HttpStatus.OK);		
	}

	private void updateAcquirePointsRequestMandatoryInfo(TransactionRequest request) {
		LoyaltyTransaction transaction;
		if(request == null)  {
			log.info("input request is not set");
			throw new NoResultException("No request details provided");
		}
		transaction = request.getTransaction();
		if(transaction == null)  {
			log.info("input transaction is not set");
			throw new NoResultException("No transaction details provided");
		}
		BigDecimal amount =  transaction.getTransactionAmount(); 
		String source = transaction.getSource();
		if(amount == null | amount == BigDecimal.ZERO | source == null | source.isEmpty())  {
			log.info("input transaction details amount or source not set");
			throw new NoResultException("No transaction details provided - amount or source not set or invalid");
		}   
		if(transaction.getTransactionDate() == null){
			log.info("input transaction date details is not set so defaulting to current date");
			transaction.setTransactionDate(Calendar.getInstance());
		}
		if(transaction.getType() == null){
			log.info("input transaction date details is not set so defaulting to current date");
			transaction.setType(TransactionType.ACQUIRE);
		}
		transaction.setStatus(TransactionStatus.SUCCESS); 
	}
	
}
